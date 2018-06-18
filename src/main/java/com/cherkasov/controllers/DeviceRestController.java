package com.cherkasov.controllers;

import com.cherkasov.Configuration;
import com.cherkasov.entities.ClientReference;
import com.cherkasov.entities.Credential;
import com.cherkasov.entities.Device;
import com.cherkasov.exceptions.ClientNotFoundException;
import com.cherkasov.repositories.DataRepository;
import com.cherkasov.repositories.DeviceDAO;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.io.IOException;
import java.util.ArrayList;

import com.cherkasov.utils.Helper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Iterator;
import java.util.List;

import static com.cherkasov.utils.Helper.createHeaders;
import static com.cherkasov.utils.Helper.getControllerName;
import static com.cherkasov.utils.Helper.getDeviceName;

@Slf4j
@RestController
@RequestMapping("/api/v1/device/{id}")
public class DeviceRestController {

    private static final String DEVICE_PATTERN = "ZWayVDev";

    @Autowired
    private DeviceDAO deviceDAO;

    @Autowired
    private DataRepository repository;

    @ApiOperation(value = "Получить список всех устройств", notes = "Список всех существующих устройств на контроллере {id}", produces = "application/json")
    @RequestMapping(value = "/get/all", method = RequestMethod.GET)
    public List<Device> getAllDevices(
        @ApiParam(value = "{id} контроллера", required = true)
        @PathVariable("id") String controllerId) {

        log.debug("ControllerId={}", controllerId);

        return deviceDAO.getAll(controllerId);
    }

    /**
     * Delete devices with NULL name
     * @param controllerId
     * @return
     */
    @ApiOperation(value = "Удалить все пустые устройства", notes = "Удалить все устройства из базы {id} которых null", produces = "application/json")
    @RequestMapping(value = "/remove/all/null", method = RequestMethod.DELETE)
    public List<Device> deleteAllNullDevices(
            @ApiParam(value = "{id} контроллера", required = true)
            @PathVariable("id") String controllerId) {

        log.debug("ControllerId={}", controllerId);

        return deviceDAO.deleteAllNull(controllerId);
    }

    @ApiOperation(value = "Удалить все устройства", notes = "Удалить все устройства из базы", produces = "application/json")
    @RequestMapping(value = "/remove/all", method = RequestMethod.DELETE)
    public List<Device> deleteAllDevices(
            @ApiParam(value = "{id} контроллера", required = true)
            @PathVariable("id") String controllerId) {

        log.debug("ControllerId={}", controllerId);

        return deviceDAO.deleteAll(controllerId);
    }

    @ApiOperation(value = "Получить список всех устройств с контроллера", notes = "Запросить список всех существующих устройств прямо с контроллера", produces = "application/json")
    @RequestMapping(value = "/get/all/actual", method = RequestMethod.GET)
    public List<Device> getAllDevicesFromController(
            @ApiParam(value = "{id} контроллера", required = true)
            @PathVariable("id") String controllerId) {

        log.debug("ControllerId={}", controllerId);
        List<Device> devices = getFromController(controllerId);
        saveAllDevicesToDb(devices, controllerId);

//        deviceDAO.insertJson("",controllerId);
        return devices;
    }


    @ApiOperation(value = "Получить описание устройства", notes = "Получить описание устройства {device} для контроллера {id}", produces = "application/json")
    @RequestMapping(value = "/get/{device}", method = RequestMethod.GET)
    public Device getOneDevice(
            @ApiParam(value = "{id} контроллера", required = true)
            @PathVariable("id") String controllerId,
            @ApiParam(value = "{device} устройство", required = true)
            @PathVariable("device") String deviceId) {

        log.debug("ControllerId={}, deviceId={}", controllerId, deviceId);

        return deviceDAO.findByName(deviceId, controllerId);
    }

    @ApiOperation(value = "Получить описание устройства (комбинированный id)", notes = "Получить описание устройства  для контроллера, {id} указыватся в виде controller_id::device_id", produces = "application/json")
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public Device getOneDeviceCombine(
            @ApiParam(value = "{id}::{device} контроллер::устройство", required = true)
            @PathVariable("id") String controllerDevice) {

        String controllerId = getControllerName(controllerDevice);
        String deviceId = getDeviceName(controllerDevice);

        log.debug("ControllerId={}, deviceId={}", controllerId, deviceId);

        return deviceDAO.findByName(deviceId, controllerId);
    }


    private String getDataFromController(String apiKey) throws ClientNotFoundException {

        List<ClientReference> clientReference = repository.getClientReferenceByApiKey(apiKey);
        if (clientReference == null || clientReference.isEmpty()) {
            throw new ClientNotFoundException();
        }
        String dataFromClient = getDataFromClient(clientReference.get(0).getHost(), apiKey);

        return dataFromClient;
    }

    private String getDataFromClient(String host, String apiKey) {

        if (host == null) {
            return "null";
        }

        RestTemplate restTemplate = new RestTemplate();
        //get by
        String clientURL = Configuration.deviceData.replace("*", host);
        List<Credential> credentials = repository.getCredentialByApiKey(apiKey);
        HttpHeaders httpHeaders;
        if (credentials == null || credentials.isEmpty()) {
            httpHeaders = createHeaders(Configuration.clientLogin, Configuration.clientPassword);
        } else {
            httpHeaders = createHeaders(credentials.get(0).getLogin(), credentials.get(0).getPassword());
        }
        ResponseEntity<String> response = restTemplate.exchange(clientURL, HttpMethod.GET, new HttpEntity<String>(httpHeaders), String.class);

        return response.getBody();
    }

    /**
     * Send query to controller and get all devices from it
     *
     * @param controllerId
     * @return
     */
    private List<Device> getFromController(String controllerId) {

        String dataFromController = getDataFromController(controllerId);


        List<Device> devices = new ArrayList<>();

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = null;
        try {
            root = mapper.readTree(dataFromController);
        } catch (IOException e) {
            log.error("cannot parse json with devices");
        }

        JsonNode jsonNode = root.get("data").get("devices");
        Iterator<JsonNode> iterator = jsonNode.iterator();
        while (iterator.hasNext()) {
            JsonNode node = iterator.next();
            Device device = new Device();
            device.setDeviceTypeString(node.get("deviceType").asText());
            device.setName(node.get("id").asText());
            JsonNode metrics = node.get("metrics");
            device.setGivenName(metrics.get("title").asText());
            device.setIsFailed(metrics.path("isFailed").asBoolean());
            device.setProbeTitle(metrics.path("probeTitle").asText());
            device.setModificationTime(metrics.path("modificationTime").asLong());
//            device.setLastReceived();
//            device.setLastSend();

            if (device.getName() == null || device.getName().isEmpty() || !device.getName().contains(DEVICE_PATTERN)) {
                continue;
            }

            devices.add(device);
        }

        return devices;
    }

    /**
     * Saves all devices into db
     * @param devices
     * @param controllerId
     */
    private void saveAllDevicesToDb(List<Device> devices, String controllerId) {

        deviceDAO.updateAll(devices, controllerId);
    }

}

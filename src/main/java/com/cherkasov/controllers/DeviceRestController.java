package com.cherkasov.controllers;

import com.cherkasov.Configuration;
import com.cherkasov.entities.ClientReference;
import com.cherkasov.entities.Credential;
import com.cherkasov.entities.Device;
import com.cherkasov.exceptions.ClientNotFoundException;
import com.cherkasov.repositories.DataRepository;
import com.cherkasov.repositories.DeviceDAO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

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

@Slf4j
@RestController
@RequestMapping("/api/v1/device/{id}")
public class DeviceRestController {

    private static final String DEVICE_PATTERN = "ZWayVDev";

    @Autowired
    private DeviceDAO deviceDAO;

    @Autowired
    private DataRepository repository;


    @RequestMapping(value = "/get/all", method = RequestMethod.GET)
    public List<Device> getAllDevices(@PathVariable("id") String controllerId) {

        log.debug("ControllerId={}", controllerId);

        return deviceDAO.getAll(controllerId);
    }

    /**
     * Delete devices with NULL name
     * @param controllerId
     * @return
     */
    @RequestMapping(value = "/remove/all/null", method = RequestMethod.DELETE)
    public List<Device> deleteAllNullDevices(@PathVariable("id") String controllerId) {

        log.debug("ControllerId={}", controllerId);

        return deviceDAO.deleteAllNull(controllerId);
    }

    @RequestMapping(value = "/remove/all", method = RequestMethod.DELETE)
    public List<Device> deleteAllDevices(@PathVariable("id") String controllerId) {

        log.debug("ControllerId={}", controllerId);

        return deviceDAO.deleteAll(controllerId);
    }

    @RequestMapping(value = "/get/all/actual", method = RequestMethod.GET)
    public List<Device> getAllDevicesFromController(@PathVariable("id") String controllerId) {

        log.debug("ControllerId={}", controllerId);
        List<Device> devices = getFromController(controllerId);
        saveAllDevicesToDb(devices, controllerId);

//        deviceDAO.insertJson("",controllerId);
        return devices;
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

    private void saveAllDevicesToDb(List<Device> devices, String controllerId) {

        deviceDAO.insertAll(devices, controllerId);
    }

    @RequestMapping(value = "/get/{device}", method = RequestMethod.GET)
    public Device getOneDevice(@PathVariable("id") String controllerId, @PathVariable("device") String deviceId) {

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

}

package com.cherkasov.controllers;

import com.cherkasov.Configuration;
import com.cherkasov.entities.ClientReference;
import com.cherkasov.entities.Credential;
import com.cherkasov.entities.Device;
import com.cherkasov.entities.TimeSeriesData;
import com.cherkasov.exceptions.ClientNotFoundException;
import com.cherkasov.repositories.DataDAO;
import com.cherkasov.repositories.DataRepository;
import com.cherkasov.utils.Helper;
import com.mongodb.util.JSON;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.List;

import static com.cherkasov.utils.Helper.createHeaders;
import static com.cherkasov.utils.Helper.getControllerName;
import static com.cherkasov.utils.Helper.getDeviceName;

@Slf4j
@RestController
@RequestMapping("/api/v1/data/{id}")
public class DataRestController {

    @Autowired
    private DataDAO dataDAO;

    // TODO: 13.05.2018 change to service
    @Autowired
    private DataRepository repository;

    @ApiOperation(value = "Получить список всех показаний", notes = "Данные с контроллера {id} и устройства {device}", produces = "application/json")
    @RequestMapping(value = "/get/all/{device}", method = RequestMethod.GET)
    public ResponseEntity<List<TimeSeriesData>> getAllByDeviceId(@ApiParam(value = "id контроллера", required = true) @PathVariable("id") String controllerId, @ApiParam(value = "id датчика", required = true) @PathVariable("device") String deviceId) {

        log.debug("ControllerId={}, deviceId={}", controllerId, deviceId);
        List<TimeSeriesData> allByDeviceId = dataDAO.findAllByDeviceId(deviceId, controllerId);
        HttpStatus status = HttpStatus.OK;
        if (allByDeviceId == null) {
            return new ResponseEntity<>(Collections.emptyList(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(allByDeviceId, HttpStatus.OK);
    }

    @ApiOperation(value = "Получить список всех показаний", notes = "Данные для контроллера и устройства, {id} указыватся в виде id::device")
    @RequestMapping(value = "/get/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TimeSeriesData>> getAllByDeviceIdCombine(@PathVariable("id") String controllerDevice) {
        String controllerId = getControllerName(controllerDevice);
        String deviceId = getDeviceName(controllerDevice);
        log.debug("ControllerId={}, deviceId={}", controllerId, deviceId);
        List<TimeSeriesData> allByDeviceId = dataDAO.findAllByDeviceId(deviceId, controllerId);
        HttpStatus status = HttpStatus.OK;
        if (allByDeviceId == null) {
            return new ResponseEntity<>(Collections.emptyList(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(allByDeviceId, HttpStatus.OK);
    }

    @ApiOperation(value = "Получить список всех показаний для контроллера и устройства, {id} указыватся в виде id::device")
    @RequestMapping(value = "/get/last/{device}", method = RequestMethod.GET)
    public ResponseEntity<TimeSeriesData> getLastByDeviceId(@PathVariable("id") String controllerId, @PathVariable("device") String deviceId) {

        log.debug("ControllerId={}, deviceId={}", controllerId, deviceId);

        TimeSeriesData lastByDeviceId = dataDAO.findLastByDeviceId(deviceId, controllerId);
        HttpStatus status = HttpStatus.OK;
        if (lastByDeviceId == null) {
//            status = HttpStatus.NO_CONTENT;
            return new ResponseEntity<>((TimeSeriesData)null, status);
        }
        return new ResponseEntity<>(lastByDeviceId, status);
    }

    @ApiOperation(value = "Получить список всех показаний для контроллера и устройства, {id} указыватся в виде id::device")
    @RequestMapping(value = "/get/last", method = RequestMethod.GET)
    public ResponseEntity<TimeSeriesData> getLastByDeviceIdCombine(@PathVariable("id") String controllerDevice) {
        String controllerId = getControllerName(controllerDevice);
        String deviceId = getDeviceName(controllerDevice);
        log.debug("ControllerId={}, deviceId={}", controllerId, deviceId);
        TimeSeriesData lastByDeviceId = dataDAO.findLastByDeviceId(deviceId, controllerId);
        HttpStatus status = HttpStatus.OK;
        if (lastByDeviceId == null) {
            status = HttpStatus.NO_CONTENT;
        }
        return new ResponseEntity<>(lastByDeviceId, status);
    }

    @ApiOperation(value = "Получить список всех показаний для контроллера и устройства, {id} указыватся в виде id::device")
    @RequestMapping(value = "/get/actual/{device}", method = RequestMethod.GET)
    public ResponseEntity<String> getActualFromControllerByDeviceId(@PathVariable("id") String controllerId, @PathVariable("device") String deviceId) {

        log.debug("ControllerId={}, deviceId={}", controllerId, deviceId);

        String dataFromController = getDataFromController(controllerId, deviceId);

        HttpStatus status = HttpStatus.OK;
        if (dataFromController == null) {
            status = HttpStatus.NOT_FOUND;
        }
        // TODO: 13.05.2018 save to base or return as is?
        // TODO: 20.05.2018 parse JSON to convenient form?
        return new ResponseEntity<>(dataFromController, status);
    }

    /**
     * Processing ID from combination of controller and device, like e8639832111cffa939ed53e765ecb17d::ZWayVDev_zway_5-0-49-1
     * @param controllerDevice
     * @return
     */
    @ApiOperation(value = "Получить список всех показаний для контроллера и устройства, {id} указыватся в виде id::device")
    @RequestMapping(value = "/get/actual", method = RequestMethod.GET)
    public ResponseEntity<String> getActualFromControllerByDeviceIdCombine(@PathVariable("id") String controllerDevice) {

        String controllerId = getControllerName(controllerDevice);
        String deviceId = getDeviceName(controllerDevice);

        log.debug("ControllerId={}, deviceId={}", controllerId, deviceId);

        String dataFromController = getDataFromController(controllerId, deviceId);
        HttpStatus status = HttpStatus.OK;
        if (dataFromController == null) {
            status = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<>(dataFromController, status);
    }

    @ApiOperation(value = "Получить список всех показаний для контроллера и устройства, {id} указыватся в виде id::device")
    @RequestMapping(value = "/save/{deviceId}", method = RequestMethod.POST)
    public ResponseEntity<Boolean> saveData(@PathVariable("id") String controllerId, @PathVariable("deviceId") String deviceId, @RequestBody TimeSeriesData entity, HttpServletRequest request) {

        log.debug("ControllerId={}, deviceId={}, body={}", controllerId, deviceId, entity);

        // TODO: 20.05.2018 make Gson parser
//        TimeSeriesData entity = (TimeSeriesData)JSON.parse(body) ;

        // TODO: 13.05.2018 make cash here

        //if client does`not registered then register him
        registerClient(controllerId, request);

        dataDAO.insert(entity, controllerId);

        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @ApiOperation(value = "Получить список всех показаний для контроллера и устройства, {id} указыватся в виде id::device")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseEntity<Boolean> saveDataCombine(@PathVariable("id") String controllerDevice, @RequestBody TimeSeriesData entity, HttpServletRequest request) {
        String controllerId = getControllerName(controllerDevice);
        String deviceId = getDeviceName(controllerDevice);
        log.debug("ControllerId={}, deviceId={}, body={}", controllerId, deviceId, entity);

        //if client does`not registered then register him
        registerClient(controllerId, request);

        dataDAO.insert(entity, controllerId);

        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @ApiOperation(value = "Получить список всех показаний для контроллера и устройства, {id} указыватся в виде id::device")
    @RequestMapping(value = "/save/json", method = RequestMethod.POST)
    public ResponseEntity<Boolean> saveJSONData(@PathVariable("id") String controllerId, @RequestBody String body, HttpServletRequest request) {

        log.debug("ControllerId={}, body={}", controllerId, body);

        // TODO: 13.05.2018 make cash here

        //if client does`not registered then register him
        registerClient(controllerId, request);

        dataDAO.insertJson(body, controllerId);

        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @ApiOperation(value = "Получить список всех показаний для контроллера и устройства, {id} указыватся в виде id::device")
    @RequestMapping(value = "/save/all", method = RequestMethod.POST)
    public ResponseEntity<Boolean> saveAllData(@PathVariable("id") String controllerId, @RequestBody List<TimeSeriesData> entity, HttpServletRequest request) {

        log.debug("ControllerId={}, body={}", controllerId, entity.toString());

        // TODO: 13.05.2018 make cash here

        //if client does`not registered then register him
        registerClient(controllerId, request);

        dataDAO.insertAll(entity, controllerId);

        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    private void registerClient(String controllerId, HttpServletRequest request) {

        List<ClientReference> clientReferences = repository.getClientReferenceByApiKey(controllerId);
        if (clientReferences == null || clientReferences.isEmpty()) {
            ClientReference clientReference = new ClientReference();
            clientReference.setHost(Helper.getClientIp(request));
            clientReference.setApiKey(controllerId);
            repository.saveEntity(clientReference);
        }
    }

    private String getDataFromController(String apiKey, String device) throws ClientNotFoundException {

        List<ClientReference> clientReference = repository.getClientReferenceByApiKey(apiKey);
        if (clientReference == null || clientReference.isEmpty()) {
            throw new ClientNotFoundException();
        }
        String dataFromClient = getDataFromClient(clientReference.get(0).getHost(), apiKey, device);

        return dataFromClient;
    }

    private String getDataFromClient(String host, String apiKey, String device) {

        if (host == null) {
            return "null";
        }

        RestTemplate restTemplate = new RestTemplate();
        //get by
        String clientURL = Configuration.clientData.replace("*", host) + device;
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

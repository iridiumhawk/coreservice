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
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.Charset;
import java.util.List;

import static com.cherkasov.utils.Helper.createHeaders;

@Slf4j
@RestController
@RequestMapping("/api/v1/data/{id}")
public class DataRestController {

    @Autowired
    private DataDAO dataDAO;

    // TODO: 13.05.2018 change to service
    @Autowired
    private DataRepository repository;


    @RequestMapping(value = "/get/all/{device}", method = RequestMethod.GET)
    public List<TimeSeriesData> getAllByDeviceId(@PathVariable("id") String controllerId, @PathVariable("device") String deviceId) {

        log.debug("ControllerId={}, deviceId={}", controllerId, deviceId);
        return dataDAO.findAllByDeviceId(deviceId, controllerId);
    }

    @RequestMapping(value = "/get/last/{device}", method = RequestMethod.GET)
    public TimeSeriesData getLastByDeviceId(@PathVariable("id") String controllerId, @PathVariable("device") String deviceId) {

        log.debug("ControllerId={}, deviceId={}", controllerId, deviceId);

        return dataDAO.findLastByDeviceId(deviceId, controllerId);
    }

    @RequestMapping(value = "/get/actual/{device}", method = RequestMethod.GET)
    public String getActualFromControllerByDeviceId(@PathVariable("id") String controllerId, @PathVariable("device") String deviceId) {

        log.debug("ControllerId={}, deviceId={}", controllerId, deviceId);

        String dataFromController = getDataFromController(controllerId, deviceId);

        // TODO: 13.05.2018 save to base or return as is?
        // TODO: 20.05.2018 parse JSON to convenient form?
        return dataFromController;
    }

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

    @RequestMapping(value = "/save/json", method = RequestMethod.POST)
    public ResponseEntity<Boolean> saveJSONData(@PathVariable("id") String controllerId, @RequestBody String body, HttpServletRequest request) {

        log.debug("ControllerId={}, body={}", controllerId, body);

        // TODO: 13.05.2018 make cash here

        //if client does`not registered then register him
        registerClient(controllerId, request);

        dataDAO.insertJson(body, controllerId);

        return new ResponseEntity<>(true, HttpStatus.OK);
    }

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

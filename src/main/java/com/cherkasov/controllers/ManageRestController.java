package com.cherkasov.controllers;

import com.cherkasov.Configuration;
import com.cherkasov.entities.BasicCommand;
import com.cherkasov.entities.ClientReference;
import com.cherkasov.entities.Credential;
import com.cherkasov.exceptions.ClientNotFoundException;
import com.cherkasov.repositories.DataRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
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

import java.nio.charset.Charset;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/manage/{id}")
public class ManageRestController {
    @Autowired
    private DataRepository repository;


    /***
     * Command for Basic command class. Only on and off commands is available
     * @param controllerId
     * @param deviceId
     * @param command
     * @return
     */
    @RequestMapping(value = "/set/{device}/{command}", method = RequestMethod.GET)
    public String sendCommandToDevice(@PathVariable("id") String controllerId, @PathVariable("device") String deviceId, @PathVariable("command") String command) {

        log.debug("ControllerId={}, deviceId={}, command={}", controllerId, deviceId, command);

        String dataFromController = sendCommandToController(controllerId, deviceId, command);

        return dataFromController;
    }

    /**
     *
     * @param controllerId
     * @param deviceId
     * @param command
     * @return
     */
    // TODO: 16.05.2018 may be RequestMethod.GET or PUT?
    @Deprecated
    @RequestMapping(value = "/set/{device}/{commandclass}/{value}", method = RequestMethod.GET)
    public String sendParameterToDevice(@PathVariable("id") String controllerId, @PathVariable("device") String deviceId, @PathVariable("commandclass") String command) {

        log.debug("ControllerId={}, deviceId={}, command={}", controllerId, deviceId, command);

//        String dataFromController = sendCommandToController(controllerId, deviceId, command);

        // TODO: 17.05.2018 which API from zwave have to use?
        return "don`t work yet";
    }


    private String sendCommandToController(String apiKey, String device, String command) {
        List<ClientReference> clientReference = repository.getClientReferenceByApiKey(apiKey);
        if (clientReference == null || clientReference.isEmpty()) {
            throw new ClientNotFoundException();
        }

        String host = clientReference.get(0).getHost();

        if (host == null) {
            return "null";
        }

        RestTemplate restTemplate = new RestTemplate();
        //Basic command class
        String clientURL = Configuration.clientData.replace("*", host) + device + "/" + BasicCommand.run(command);
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

    private HttpHeaders createHeaders(String username, String password) {

        return new HttpHeaders() {{
            String auth = username + ":" + password;
            byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("UTF-8")));
            String authHeader = "Basic " + new String(encodedAuth);
            set("Authorization", authHeader);
        }};
    }
}

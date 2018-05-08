package com.cherkasov.controllers;

import com.cherkasov.Configuration;
import com.cherkasov.entities.ClientReference;
import com.cherkasov.entities.User;
import com.cherkasov.repositories.DataRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v1/register")
public class RegisterController {

    @Autowired
    private DataRepository repository;

    @RequestMapping("/get/new")
    public ClientReference getNew() {

        return new ClientReference();
    }
/*
    @RequestMapping("/user/new")
    public User getNewUser() {

        final User user = new User();
        repository.saveUser(user);
        return user;
    }*/

    @RequestMapping("/get/all")
    public List<ClientReference> getAll() {

        return repository.getAllEntity();
    }

    @RequestMapping("/get/{id}")
    public ClientReference getById(@PathVariable("id") int entityId) {

        return repository.getById(entityId);
    }

    @RequestMapping("/remove/{id}")
    public ResponseEntity<Boolean> removeById(@PathVariable("id") int entityId, @RequestHeader HttpHeaders httpHeaders) {

        final Map<String, String> singleValueMap = httpHeaders.toSingleValueMap();
        final String apiKey = singleValueMap.get("api-key");
        log.trace("Api-Key: {}", apiKey);

/*    User user = repository.getByApiKey(apiKey);
    log.trace("Current role {}", user.getRole());

    if (RoleType.ADMIN.equals(user.getRole())) {
      return new ResponseEntity<>(repository.removeById(entityId), HttpStatus.OK);
    }*/
        return new ResponseEntity<>(false, HttpStatus.UNAUTHORIZED);
    }

    @RequestMapping(value = "/save/all", method = RequestMethod.POST)
    public ResponseEntity<Boolean> saveAll(@RequestBody List<ClientReference> entities) {

        for (ClientReference entity : entities) {
            repository.saveEntity(entity);
        }
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @RequestMapping(value = "/save/one", method = RequestMethod.POST)
    public ResponseEntity<ClientReference> saveOne(@RequestBody ClientReference entity) {
        // TODO: 22.04.2018 check for apikey - if it already exist then update record with this apikey

        String host = entity.getHost();

        startHostRequest(host);

        ClientReference clientReference = repository.getByHost(host);
        if (clientReference == null) {
            entity.setId(null);
//            entity.setApiKey(getApiKeyFromClient(host));
            return new ResponseEntity<>(repository.saveEntity(entity), HttpStatus.OK);
        }

//        clientReference.setApiKey(getApiKeyFromClient(host));
        return new ResponseEntity<>(repository.saveEntity(clientReference), HttpStatus.OK);
    }

    private void startHostRequest(String host) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    log.debug("sleep before host request");
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    //nothing
                }
                ClientReference clientReference = repository.getByHost(host);
                clientReference.setApiKey(getApiKeyFromClient(host));
                repository.saveEntity(clientReference);
            }
        }).start();
    }

    private String getApiKeyFromClient(String host) {

        if (host == null) {
            return "null";
        }
        RestTemplate restTemplate = new RestTemplate();
        String clientURL = Configuration.clientURL.replace("*", host);
        ResponseEntity<String> response = restTemplate.exchange(clientURL, HttpMethod.GET, new HttpEntity<String>(createHeaders(Configuration.clientLogin, Configuration.clientPassword)), String.class);

//                restTemplate.getForEntity(clientURL, String.class);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = null;
        try {
            root = mapper.readTree(response.getBody());
        } catch (IOException e) {
            log.error("Cannot parse json");
            return null;
        }
        JsonNode uuid = root.path("value");

        return uuid.asText();
    }

    private HttpHeaders createHeaders(String username, String password) {

        return new HttpHeaders() {{
            String auth = username + ":" + password;
            byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("UTF-8")));
            String authHeader = "Basic " + new String(encodedAuth);
            set("Authorization", authHeader);
        }};
    }

/*    @Deprecated
    private ClientHttpRequestFactory getClientHttpRequestFactory(int timeout) {

        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        clientHttpRequestFactory.setConnectTimeout(timeout);
        return clientHttpRequestFactory;
    }*/

    //    @Bean
//    @Autowired
/*    public RestOperations rest(RestTemplateBuilder restTemplateBuilder) {

        return restTemplateBuilder.basicAuthorization(Configuration.clientLogin, Configuration.clientPassword).setConnectTimeout(5000).build();
    }*/
}

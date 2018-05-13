package com.cherkasov.controllers;

import com.cherkasov.Configuration;
import com.cherkasov.entities.ClientReference;
import com.cherkasov.repositories.DataRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.DomainEvents;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v1/register")
public class RegisterRestController {

    @Autowired
    private DataRepository repository;

    @RequestMapping("/get/new")
    public ClientReference getNew() {

        return new ClientReference();
    }

    @RequestMapping("/get/all")
    public List<ClientReference> getAll() {

        return repository.getAllEntity();
    }

    @RequestMapping("/get/id/{id}")
    public ClientReference getById(@PathVariable("id") int entityId) {
        log.trace("entityId={}", entityId);
        return repository.getClientReferenceById(entityId);
    }

    @RequestMapping("/get/key/{id}")
    public List<ClientReference> getByEntityId(@PathVariable("id") String apiKey) {

        log.trace("Api key={}", apiKey);
        return repository.getClientReferenceByApiKey(apiKey);
    }

    /**
     * Remove entity by id with authorisation (api-key)
     *
     * @param entityId
     * @param httpHeaders
     * @return
     */
    @Deprecated
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
/*
    @RequestMapping(value = "/save/all", method = RequestMethod.POST)
    public ResponseEntity<Boolean> saveAll(@RequestBody List<ClientReference> entities) {

        for (ClientReference entity : entities) {
            repository.saveEntity(entity);
        }
        return new ResponseEntity<>(true, HttpStatus.OK);
    }*/

    @RequestMapping(value = "/save/one", method = RequestMethod.POST)
    public ResponseEntity<ClientReference> saveOne(@RequestBody ClientReference entity, HttpServletRequest request) {
        // TODO: 22.04.2018 check for apikey - if it already exist then update record with this apikey
        log.trace("Client entity: {}", entity);

        String host = getClientIp(request);
        String apiKey = entity.getApiKey();
        log.trace("Client host: {}", host);

        List<ClientReference> clientReference = repository.getClientReferenceByApiKey(apiKey);
        if (clientReference == null || clientReference.isEmpty()) {
            entity.setId(null);
            entity.setHost(host);
            return new ResponseEntity<>(repository.saveEntity(entity), HttpStatus.OK);
        }

        if (clientReference.size() > 1) {
            for (ClientReference reference : clientReference) {
                repository.removeClientReferenceById(reference.getId());
            }
            return new ResponseEntity<>(repository.saveEntity(entity), HttpStatus.OK);

        } else {
            clientReference.get(0).setHost(host);
            return new ResponseEntity<>(repository.saveEntity(clientReference.get(0)), HttpStatus.OK);
        }

    }

    private String getClientIp(HttpServletRequest request) {

        String remoteAddr = "";

        if (request != null) {
            remoteAddr = request.getHeader("X-FORWARDED-FOR");
            if (remoteAddr == null || "".equals(remoteAddr)) {
                remoteAddr = request.getRemoteAddr();
            }
        }

        return remoteAddr;
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

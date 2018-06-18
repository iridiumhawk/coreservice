package com.cherkasov.controllers;

import com.cherkasov.entities.ClientReference;
import com.cherkasov.repositories.DataRepository;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v1/register")
public class RegisterRestController {

    @Autowired
    private DataRepository repository;


    @ApiOperation(value = "Получить новый объект клиета", notes = "Создает новый объект и возвращает его (для тестов)", produces = "application/json")
    @RequestMapping(value = "/get/new", method = RequestMethod.GET)
    public ClientReference getNew() {

        return new ClientReference();
    }

    @ApiOperation(value = "Получить всех зарегистрированных клиентов", notes = "Возвращает всех зарегистрированных клиентов из базы данных", produces = "application/json")
    @RequestMapping(value = "/get/all", method = RequestMethod.GET)
    public List<ClientReference> getAll() {

        return repository.getAllEntity();
    }

    @ApiOperation(value = "Получить клиента по id", notes = "Возвращает зарегистрированного клиента из базы данных по уникальному ключу {id}", produces = "application/json")
    @RequestMapping(value = "/get/id/{id}", method = RequestMethod.GET)
    public ClientReference getById(
            @ApiParam(value = "{id} уникальный ключ", required = true)
            @PathVariable("id") int entityId) {

        log.trace("entityId={}", entityId);
        return repository.getClientReferenceById(entityId);
    }

    @ApiOperation(value = "Получить клиента по uid", notes = "Возвращает зарегистрированного клиента из базы данных по идентификатору контроллера {key}", produces = "application/json")
    @RequestMapping(value = "/get/{key}", method = RequestMethod.GET)
    public List<ClientReference> getByApiKey(
            @ApiParam(value = "{key} идентификатор контроллера", required = true)
            @PathVariable("key") String apiKey) {

        log.trace("Api key={}", apiKey);
        return repository.getClientReferenceByApiKey(apiKey);
    }

    /**
     * Remove entity by id
     *
     * @param entityId
     * @param httpHeaders
     * @return
     */
    @ApiOperation(value = "Удалить клиента по uid", notes = "Удаляет зарегистрированного клиента из базы данных по идентификатору контроллера {id}", produces = "application/json")
    @RequestMapping(value = "/remove/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Boolean> removeById(
            @ApiParam(value = "{id} идентификатор контроллера", required = true)
            @PathVariable("id") int entityId,
            @ApiParam(value = "заголовки, содержащие аутентификацию", required = true)
            @RequestHeader HttpHeaders httpHeaders) {

        final Map<String, String> singleValueMap = httpHeaders.toSingleValueMap();
        final String apiKey = singleValueMap.get("api-key");
        log.trace("Api-Key: {}", apiKey);

        return new ResponseEntity<>(repository.removeClientReferenceById(entityId), HttpStatus.OK);
    }

    @ApiOperation(value = "Сохранить клиента (зарегистрировать)", notes = "Регистрирует клиента в базе данных (IP, id, время регистрации)", produces = "application/json", consumes = "application/json")
    @RequestMapping(value = "/save/one", method = RequestMethod.POST)
    public ResponseEntity<ClientReference> saveOne(
            @ApiParam(value = "информация о клиенте (ClientReference.class)", required = true)
            @RequestBody ClientReference entity, HttpServletRequest request) {

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

    /**
     * Get IP address for client
     * @param request
     * @return
     */
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

package com.cherkasov.controllers;

import com.cherkasov.entities.Credential;
import com.cherkasov.repositories.DataRepository;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/credential")
public class CredentialRestController {

    @Autowired
    private DataRepository repository;

    @ApiOperation(value = "Сохранить данные аутентификации", notes = "Сохраняет в базу логин и пароль для доступа к API zway сервера для контроллера с именем 'apiKey'", consumes = "application/json", produces = "application/json")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseEntity<Credential> saveOne(
        @ApiParam(value = "аутентификационные данные (Credential.class)", required = true)
        @RequestBody Credential entity) {

        log.trace("Save credential={}", entity);

        List<Credential> credential = repository.getCredentialByApiKey(entity.getApiKey());
        Credential credentialStored;
        if (credential == null || credential.isEmpty()) {
            entity.setId(null);
            credentialStored = repository.saveCredential(entity);
        } else {
            entity.setId(credential.get(0).getId());
            credentialStored = repository.saveCredential(entity);
        }
        return new ResponseEntity<>(credentialStored, HttpStatus.OK);
    }

    @ApiOperation(value = "Получить данные аутентификации", notes = "Получить логин и пароль для доступа к API zway сервера для контроллера", produces = "application/json")
    @RequestMapping(value = "/get/one/{id}", method = RequestMethod.GET)
    public ResponseEntity<List<Credential>> getById(
        @ApiParam(value = "{id} контроллера", required = true)
        @PathVariable("id") String apiKey) {

    log.trace("Get credential by apiKey={}", apiKey);
        return new ResponseEntity<>(repository.getCredentialByApiKey(apiKey), HttpStatus.OK);
    }

    @ApiOperation(value = "Получить все данные аутентификации", notes = "Получить все логины и пароли для доступа к API zway сервера для всех контроллеров", produces = "application/json")
    @RequestMapping(value = "/get/all", method = RequestMethod.GET)
    public ResponseEntity<List<Credential>> getAll() {
    log.trace("Get all credential");
        return new ResponseEntity<>(repository.getAllCredential(), HttpStatus.OK);
    }
}

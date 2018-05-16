package com.cherkasov.controllers;

import com.cherkasov.entities.Credential;
import com.cherkasov.repositories.DataRepository;
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

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseEntity<Credential> saveOne(@RequestBody Credential entity) {
        log.trace("Save credential={}", entity);

        List<Credential> credential = repository.getCredentialByApiKey(entity.getApiKey());
        Credential credentialStored;
        if (credential == null || credential.isEmpty()) {
            entity.setId(null);
            credentialStored = repository.saveCredential(entity);
            return new ResponseEntity<>(credentialStored, HttpStatus.OK);
        } else {
            entity.setId(credential.get(0).getId());
            credentialStored = repository.saveCredential(entity);
            return new ResponseEntity<>(credentialStored, HttpStatus.OK);
        }
    }


    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public List<Credential> getById(@PathVariable("id") String apiKey) {
    log.trace("Get credential by apiKey={}", apiKey);
        return repository.getCredentialByApiKey(apiKey);
    }
}

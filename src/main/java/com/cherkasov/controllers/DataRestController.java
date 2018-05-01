package com.cherkasov.controllers;

import com.cherkasov.entities.TimeSeriesData;
import com.cherkasov.repositories.DataDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/data/{id}")
public class DataRestController extends RootController{

    @Autowired
    private DataDAO dataDAO;

/*
    @RequestMapping("/get/all")
    public List<TimeSeriesData> getAllByControllerId(@PathVariable("id") String controllerId) {

        return null;
    }*/

    @RequestMapping("/get/all/{device}")
    public List<TimeSeriesData> getAllByDeviceId(@PathVariable("id") String controllerId, @PathVariable("device") String deviceId) {

        return dataDAO.findAllByDeviceId(deviceId, controllerId);

    }

    @RequestMapping("/get/last/{device}")
    public TimeSeriesData getLastByDeviceId(@PathVariable("id") String controllerId, @PathVariable("device") String deviceId) {

        return dataDAO.findLastByDeviceId(deviceId, controllerId);
    }


    @RequestMapping(value = "/save/{device}", method = RequestMethod.POST)
    public ResponseEntity<Boolean> saveData(@PathVariable("id") String controllerId, @RequestBody TimeSeriesData entity) {

        dataDAO.insert(entity, controllerId);

//        String host = entity.getHost();
//        clientReference.setApiKey(getApiKeyFromClient(host));
//        return new ResponseEntity<>(repository.saveEntity(clientReference), HttpStatus.OK);

        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @RequestMapping(value = "/save/all/{device}", method = RequestMethod.POST)
    public ResponseEntity<Boolean> saveAllData(@PathVariable("id") String controllerId, @RequestBody List<TimeSeriesData> entity) {

        dataDAO.insertAll(entity, controllerId);

        return new ResponseEntity<>(true, HttpStatus.OK);
    }

}

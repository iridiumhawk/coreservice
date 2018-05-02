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
@RequestMapping("/api/v1/data/{id}")
public class DataRestController extends RootController {

    @Autowired
    private DataDAO dataDAO;

/*
    @RequestMapping("/get/all")
    public List<TimeSeriesData> getAllByControllerId(@PathVariable("id") String controllerId) {

        return null;
    }*/

    @RequestMapping("/get/all/{device}")
    public List<TimeSeriesData> getAllByDeviceId(@PathVariable("id") String controllerId, @PathVariable("device") String deviceId) {

        log.debug("ControllerId={}, deviceId={}", controllerId, deviceId);
        return dataDAO.findAllByDeviceId(deviceId, controllerId);

    }

    @RequestMapping("/get/last/{device}")
    public TimeSeriesData getLastByDeviceId(@PathVariable("id") String controllerId, @PathVariable("device") String deviceId) {

        log.debug("ControllerId={}, deviceId={}", controllerId, deviceId);

        return dataDAO.findLastByDeviceId(deviceId, controllerId);
    }


    // TODO: 02.05.2018 remove device in path
    @RequestMapping(value = "/save/{device}", method = RequestMethod.POST)
    public ResponseEntity<Boolean> saveData(@PathVariable("id") String controllerId, @PathVariable("device") String deviceId, @RequestBody TimeSeriesData entity) {

        log.debug("ControllerId={}, deviceId={}, body={}", controllerId, deviceId, entity.toString());

        dataDAO.insert(entity, controllerId);

//        String host = entity.getHost();
//        clientReference.setApiKey(getApiKeyFromClient(host));
//        return new ResponseEntity<>(repository.saveEntity(clientReference), HttpStatus.OK);

        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @RequestMapping(value = "/save/all/{device}", method = RequestMethod.POST)
    public ResponseEntity<Boolean> saveAllData(@PathVariable("id") String controllerId, @PathVariable("device") String deviceId, @RequestBody List<TimeSeriesData> entity) {

        log.debug("ControllerId={}, deviceId={}, body={}", controllerId, deviceId, entity.toString());

        dataDAO.insertAll(entity, controllerId);

        return new ResponseEntity<>(true, HttpStatus.OK);
    }

}

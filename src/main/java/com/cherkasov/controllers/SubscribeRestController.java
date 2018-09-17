package com.cherkasov.controllers;

import com.cherkasov.entities.ClientReference;
import com.cherkasov.entities.ClientSubscription;
import com.cherkasov.repositories.DataRepository;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/subscribe/{id}")
public class SubscribeRestController {
    @Autowired
    private DataRepository repository;

    private final HttpStatus okStatus = HttpStatus.OK;


    /***

     */
    @ApiOperation(value = "Подписаться на события", notes = "Подписаться на событие на устройстве {device} на контроллере {id}.", produces = "application/json", consumes = "application/json")
    @RequestMapping(value = "/enable/{device}", method = RequestMethod.POST)
    public ResponseEntity<?> enable(
            @ApiParam(value = "{id} контроллера", required = true)
            @PathVariable("id") String controllerId,
            @ApiParam(value = "{device} устройство", required = true)
            @PathVariable("device") String deviceId,
            @ApiParam(value = "информация о подписке (ClientSubscription.class)", required = true)
            @RequestBody ClientSubscription entity) {

        log.debug("ControllerId={}, deviceId={}, subscription={}", controllerId, deviceId, entity);

        // TODO: 16.09.2018 make processing

        return new ResponseEntity<>("", okStatus);
    }

    @ApiOperation(value = "Обновить параметры подписки", notes = "Обновить параметры подписки на события на устройстве {device} на контроллере {id}.", produces = "application/json", consumes = "application/json")
    @RequestMapping(value = "/update/{device}", method = RequestMethod.POST)
    public ResponseEntity<?> update(
            @ApiParam(value = "{id} контроллера", required = true)
            @PathVariable("id") String controllerId,
            @ApiParam(value = "{device} устройство", required = true)
            @PathVariable("device") String deviceId,
            @ApiParam(value = "информация о подписке (ClientSubscription.class)", required = true)
            @RequestBody ClientSubscription entity) {

        log.debug("ControllerId={}, deviceId={}, subscription={}", controllerId, deviceId, entity);

        // TODO: 16.09.2018 make processing

        return new ResponseEntity<>("", okStatus);
    }


    @ApiOperation(value = "Отписаться от всех событий на устройстве", notes = "Отписаться от всех событий на устройстве {device} на контроллере {id}.", produces = "application/json", consumes = "application/json")
    @RequestMapping(value = "/disable/{device}", method = RequestMethod.GET)
    public ResponseEntity<?> disableDevice(
            @ApiParam(value = "{id} контроллера", required = true)
            @PathVariable("id") String controllerId,
            @ApiParam(value = "{device} устройство", required = true)
            @PathVariable("device") String deviceId) {

        log.debug("ControllerId={}, deviceId={}", controllerId, deviceId);

        // TODO: 16.09.2018 make processing

        return new ResponseEntity<>("", okStatus);
    }

    @ApiOperation(value = "Отписаться от всех событий на контроллере", notes = "Отписаться от всех событий на контроллере {id}.", produces = "application/json", consumes = "application/json")
    @RequestMapping(value = "/disableall", method = RequestMethod.GET)
    public ResponseEntity<?> disableAll(
            @ApiParam(value = "{id} контроллера", required = true)
            @PathVariable("id") String controllerId) {

        log.debug("ControllerId={}", controllerId);

        // TODO: 16.09.2018 make processing

        return new ResponseEntity<>("", okStatus);
    }

}

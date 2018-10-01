package com.cherkasov.controllers;

import com.cherkasov.entities.ClientSubscription;
import com.cherkasov.services.SubscriptionService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/subscribe")
public class SubscribeRestController {

  @Autowired
  private SubscriptionService service;

    private final HttpStatus okStatus = HttpStatus.OK;

  @ApiOperation(value = "Просмотреть подписки", notes = "Просмотреть все текущие подписки на  всех устройствах.", produces = "application/json", consumes = "application/json")
  @RequestMapping(value = "/view", method = RequestMethod.GET)
  public ResponseEntity<?> view(
//            @ApiParam(value = "{id} контроллера", required = true)
//            @PathVariable("id") String controllerId,
//            @ApiParam(value = "{device} устройство", required = true)
//            @PathVariable("device") String deviceId,
//      @ApiParam(value = "информация о подписке (ClientSubscription.class)", required = true)
//      @RequestBody ClientSubscription entity
  ) {

    final List<ClientSubscription> clientSubscriptions = service.viewSubscription();

    return new ResponseEntity<>(clientSubscriptions, okStatus);
  }

    /***
      Subscribes client on event form device
     */
    @ApiOperation(value = "Подписаться на события", notes = "Подписаться на событие на устройстве {device} на контроллере {id}.", produces = "application/json", consumes = "application/json")
    @RequestMapping(value = "/enable", method = RequestMethod.POST)
    public ResponseEntity<?> enable(
//            @ApiParam(value = "{id} контроллера", required = true)
//            @PathVariable("id") String controllerId,
//            @ApiParam(value = "{device} устройство", required = true)
//            @PathVariable("device") String deviceId,
            @ApiParam(value = "информация о подписке (ClientSubscription.class)", required = true)
            @RequestBody ClientSubscription entity) {

        log.debug("subscription={}", entity);

        service.addSubscription(entity);

        return new ResponseEntity<>("", okStatus);
    }

    @ApiOperation(value = "Обновить параметры подписки", notes = "Обновить параметры подписки на события на устройстве {device} на контроллере {id}.", produces = "application/json", consumes = "application/json")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity<?> update(
//            @ApiParam(value = "{id} контроллера", required = true)
//            @PathVariable("id") String controllerId,
//            @ApiParam(value = "{device} устройство", required = true)
//            @PathVariable("device") String deviceId,
            @ApiParam(value = "информация о подписке (ClientSubscription.class)", required = true)
            @RequestBody ClientSubscription entity) {

      log.debug("subscription={}", entity);

        service.updateSubscription(entity);

        return new ResponseEntity<>("", okStatus);
    }


    @ApiOperation(value = "Отписаться от всех событий на устройстве", notes = "Отписаться от всех событий на устройстве {device} на контроллере {id}.", produces = "application/json", consumes = "application/json")
    @RequestMapping(value = "/disable/{id}/{device}", method = RequestMethod.DELETE)
    public ResponseEntity<?> disableDevice(
            @ApiParam(value = "{id} контроллера", required = true)
            @PathVariable("id") String controllerId,
            @ApiParam(value = "{device} устройство", required = true)
            @PathVariable("device") String deviceId) {

        log.debug("ControllerId={}, deviceId={}", controllerId, deviceId);

        service.removeSubscriptionDevice(controllerId, deviceId);

        return new ResponseEntity<>("", okStatus);
    }

    @ApiOperation(value = "Отписаться от всех событий на контроллере", notes = "Отписаться от всех событий на контроллере {id}.", produces = "application/json", consumes = "application/json")
    @RequestMapping(value = "/disableall/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> disableAll(
            @ApiParam(value = "{id} контроллера", required = true)
            @PathVariable("id") String controllerId) {

        log.debug("ControllerId={}", controllerId);

        service.removeAllSubscription(controllerId);

        return new ResponseEntity<>("", okStatus);
    }
}

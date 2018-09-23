package com.cherkasov.controllers;

import com.cherkasov.entities.EventLog;
import com.cherkasov.repositories.AbstractDAO;
import com.cherkasov.repositories.EventLogDAO;
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
@RequestMapping("/api/v1/log/{id}")
public class EventLogRestController {

  @Autowired
  private EventLogDAO logDAO;

  private final HttpStatus okStatus = HttpStatus.OK;

    @ApiOperation(value = "Получить лог всех событий на устройстве", notes = "Получить лог всех событий на устройстве {device} на контроллере {id}.", produces = "application/json", consumes = "application/json")
    @RequestMapping(value = "/{device}", method = RequestMethod.GET)
    public ResponseEntity<List<EventLog>> logFromDevice(
            @ApiParam(value = "{id} контроллера", required = true)
            @PathVariable("id") String controllerId,
            @ApiParam(value = "{device} устройство", required = true)
            @PathVariable("device") String deviceId) {

        log.debug("ControllerId={}, deviceId={}", controllerId, deviceId);

        List<EventLog> allByField = logDAO.getAllByField("deviceId", deviceId, controllerId);

        return new ResponseEntity<>(allByField, okStatus);
    }

    @ApiOperation(value = "Получить лог всех событий на контроллере", notes = "Получить лог всех событий на контроллере {id}.", produces = "application/json", consumes = "application/json")
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<List<EventLog>> logFromController(
            @ApiParam(value = "{id} контроллера", required = true)
            @PathVariable("id") String controllerId) {

        log.debug("ControllerId={}", controllerId);

        List<EventLog> all = logDAO.getAll(controllerId);

        return new ResponseEntity<>(all, okStatus);
    }

}

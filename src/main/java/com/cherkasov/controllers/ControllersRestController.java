package com.cherkasov.controllers;

import com.cherkasov.repositories.DeviceDAO;
import io.swagger.annotations.ApiOperation;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/controllers")
public class ControllersRestController {

    @Autowired
    private DeviceDAO deviceDAO;

    @ApiOperation(value = "Список контроллеров", notes = "Возвращает {id} всех контроллеров", produces = "application/json")
    @RequestMapping(value = "/get/all", method = RequestMethod.GET)
    public Set<String> getAllControllers() {

        log.debug("get all controllers");

        return deviceDAO.getAllControllersName();
    }
}

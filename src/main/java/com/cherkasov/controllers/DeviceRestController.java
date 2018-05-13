package com.cherkasov.controllers;

import com.cherkasov.entities.Device;
import com.cherkasov.repositories.DeviceDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/device/{id}")
public class DeviceRestController {

    @Autowired
    private DeviceDAO deviceDAO;


    @RequestMapping("/get/all")
    public List<Device> getAllDevices(@PathVariable("id") String controllerId) {

        log.debug("ControllerId={}", controllerId);

        return deviceDAO.getAll(controllerId);
    }

    @RequestMapping("/get/{device}")
    public Device getOneDevice(@PathVariable("id") String controllerId, @PathVariable("device") String deviceId) {

        log.debug("ControllerId={}, deviceId={}", controllerId, deviceId);

        return deviceDAO.findByName(deviceId, controllerId);
    }

}

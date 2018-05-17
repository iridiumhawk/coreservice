package com.cherkasov.controllers;

import com.cherkasov.entities.Device;
import com.cherkasov.repositories.DeviceDAO;
import java.util.Collections;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/device/{id}")
public class DeviceRestController {

    @Autowired
    private DeviceDAO deviceDAO;


    @RequestMapping(value = "/get/all", method = RequestMethod.GET)
    public List<Device> getAllDevices(@PathVariable("id") String controllerId) {

        log.debug("ControllerId={}", controllerId);

        return deviceDAO.getAll(controllerId);
    }

    @Deprecated
    @RequestMapping(value = "/get/all/actual", method = RequestMethod.GET)
    public List<Device> getAllDevicesFromController(@PathVariable("id") String controllerId) {

        log.debug("ControllerId={}", controllerId);
        log.warn("not yet realized!");
        List<Device> devices = getFromController(controllerId);
        saveAllDevicesToDb(devices, controllerId);

        return devices;
    }

    /**
     * Not yet realized. Send query to controller and get all devices from it
     * @param controllerId
     * @return
     */
    @Deprecated
    private List<Device> getFromController(String controllerId) {
        return Collections.emptyList();
    }

    private void saveAllDevicesToDb(List<Device> devices, String controllerId) {
        deviceDAO.insertAll(devices, controllerId);
    }

    @RequestMapping(value = "/get/{device}", method = RequestMethod.GET)
    public Device getOneDevice(@PathVariable("id") String controllerId, @PathVariable("device") String deviceId) {

        log.debug("ControllerId={}, deviceId={}", controllerId, deviceId);

        return deviceDAO.findByName(deviceId, controllerId);
    }

}

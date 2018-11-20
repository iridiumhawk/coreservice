package com.cherkasov;

import org.springframework.beans.factory.annotation.Value;

public class Configuration {
    public final static String clientURL = "http://*:8083/ZWaveAPI/Run/controller.data.uuid.value";
    public final static String clientData = "http://*:8083/ZAutomation/api/v1/devices/";
    public final static String deviceData = "http://*:8083/ZAutomation/api/v1/devices";
    public final static String deviceTypeString = "http://*:8083/ZWaveAPI/Run/zway.devices[$].data.deviceTypeString";
//    @Value("${device.command.pattern}")
    public final static String deviceCommand = "http://*:8083/";

    public final static String clientLogin = "admin";
    public final static String clientPassword = "T4535nhsdf68nMNb54";

}

package com.cherkasov.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Event {
    private String controllerId;
    private String deviceId;
    private String sensorId;
    private String value;
    private Long updateTime;

}

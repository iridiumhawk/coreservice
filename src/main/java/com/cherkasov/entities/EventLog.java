package com.cherkasov.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Document
public class EventLog {
    private String deviceId;
    private String sensorId;
    private String value;
    private Long updateTime;
    private String sendEventTime;

    public EventLog(Event event) {

        this.deviceId = event.getDeviceId();
        this.sensorId = event.getSensorId();
        this.value = event.getValue();
        this.updateTime = event.getUpdateTime();
        this.sendEventTime = LocalDateTime.now().toString();
    }
}

package com.cherkasov.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class EventLog {
    private String deviceId;
    private String sensorId;
    private String value;
    private Long updateTime;
    private String sendEventTime;

}

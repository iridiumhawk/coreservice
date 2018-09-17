package com.cherkasov.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@ToString
@NoArgsConstructor
@Document
public class ClientSubscription {
    @JsonProperty("controllerid")
    private String controllerId;
    @JsonProperty("deviceid")
    private String deviceId;
    @JsonProperty("sensorid")
    private String sensorId;
    @JsonProperty("value")
    private List<String> value; // TODO: 17.09.2018 make List of values
    @JsonProperty("starttime")
    private Long startTime;
    @JsonProperty("endtime")
    private Long endTime;
    @JsonProperty("notificationchannel")
    private List<String> notificationChannel;
}

package com.cherkasov.entities;

import com.cherkasov.channel.Channel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@ToString
@NoArgsConstructor
@Document
public class ClientSubscription {
    @JsonProperty("controllerid")
    protected String controllerId;
    @JsonProperty("deviceid")
    protected String deviceId;
    @JsonProperty("sensorid")
    protected String sensorId;
    @JsonProperty("value")
    protected List<String> value; //ustavka
    @JsonProperty("starttime")
    protected Long startTime;
    @JsonProperty("endtime")
    protected Long endTime;
    @JsonProperty("message")
    @Setter
    protected String message;
    @JsonProperty("notifications")
    protected List<String> notifications; // TODO: 22.09.2018 take it from profile
    @Transient
    @JsonIgnore
    @Setter
    protected List<Channel> notificationChannel;

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ClientSubscription that = (ClientSubscription) o;

        if (controllerId != null ? !controllerId.equals(that.controllerId) : that.controllerId != null) return false;
        if (deviceId != null ? !deviceId.equals(that.deviceId) : that.deviceId != null) return false;
        return sensorId != null ? sensorId.equals(that.sensorId) : that.sensorId == null;
    }

    @Override
    public int hashCode() {

        int result = controllerId != null ? controllerId.hashCode() : 0;
        result = 31 * result + (deviceId != null ? deviceId.hashCode() : 0);
        result = 31 * result + (sensorId != null ? sensorId.hashCode() : 0);
        return result;
    }
}

package com.cherkasov.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Setter
@Getter
@ToString
@Document
public class TimeSeriesData {
    @Id
    private String id;
    @Field("deviceid")
    @JsonProperty("deviceid")
    private String deviceId;
    private Float value;
    @Field("updatetime")
    @JsonProperty("updatetime")
    private Long updateTime;

    public TimeSeriesData() {

    }

/*    public TimeSeriesData(String id, String deviceId, Float value, Long updateTime) {

        this.id = id;
        this.deviceId = deviceId;
        this.value = value;
        this.updateTime = updateTime;
    }*/
}

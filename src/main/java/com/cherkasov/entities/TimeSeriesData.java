package com.cherkasov.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
//@JsonIgnoreProperties(ignoreUnknown = true)
public class TimeSeriesData {
    @Id
    private String id;
    @Field("deviceid")
    @JsonProperty("deviceid")
    private String deviceId;
    private String value;
    @Field("updatetime")
    @JsonProperty("updatetime")
    private Long updateTime;

    public TimeSeriesData() {

    }

}

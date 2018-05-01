package com.cherkasov.entities;

import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@ToString
@Document
public class TimeSeriesData {
    @Id
    private String id;
    private String deviceId;
    private Float value;
    private Long updateTime;
}

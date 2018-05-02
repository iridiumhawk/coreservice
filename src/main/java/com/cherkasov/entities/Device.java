package com.cherkasov.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Setter
@Getter
@ToString
@Document
public class Device {

    @Id
    private String id;
    private String name;
    private String givenName;
    private String vendorString;
    private String deviceTypeString;
    private Long lastReceived;
    private Long lastSend;
    private Boolean isFailed;
    private List<CommandClass> commandClasses;

    public Device() {

    }


}


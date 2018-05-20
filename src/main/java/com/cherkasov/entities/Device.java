package com.cherkasov.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
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
//    @JsonProperty("givenname")
    private String givenName;
    private String vendorString;
    private String deviceTypeString;
    private String probeTitle;
    private Long lastReceived;
    private Long lastSend;
    private Boolean isFailed;
    private Long modificationTime;
    private List<CommandClass> commandClasses;

    public Device() {

    }
}
/*
{"data":{"structureChanged":true,"updateTime":1526819960,"devices":[{"creationTime":1518034540,"customIcons":{},"deviceType":"switchControl","h":-1199109514,"hasHistory":true,"id":"ZWayVDev_zway_Remote_5-0-0-B","location":0,"metrics":{"icon":"gesture","level":"off","title":"Philio Technology Corp (5.0.0) Button","change":"","isFailed":false,"modificationTime":1526818376,"lastLevel":"off"},"order":{"rooms":0,"elements":0,"dashboard":0},"permanently_hidden":false,"probeType":"","tags":[],"visibility":true,"updateTime":1526818376},
*/



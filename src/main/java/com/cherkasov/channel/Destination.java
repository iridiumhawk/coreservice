package com.cherkasov.channel;

import lombok.Data;

@Data
public class Destination {
    private String endPoint;
    private String user;
    private String password;
}

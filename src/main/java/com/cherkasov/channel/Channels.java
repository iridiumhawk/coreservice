package com.cherkasov.channel;

public enum Channels {

    EMPTY("empty"),
    SMS("sms"),
    EMAIL("email");

    Channels(String name) {
        this.name = name;
    }

    private String name;

    public String getName() {
        return this.name;
    }
}

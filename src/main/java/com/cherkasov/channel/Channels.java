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

    /*
     public static Blah fromString(String text) {
    for (Blah b : Blah.values()) {
      if (b.text.equalsIgnoreCase(text)) {
        return b;
      }
    }
    return null;
  }
    */
}

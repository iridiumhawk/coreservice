package com.cherkasov.entities;

public class BasicCommand {
    private static final String command = "command";

    public static String run(String command) {

        switch (command) {
            case "on":
                return on();
            case "off":
                return off();
             default:
                 return "";
        }
    }

    private static String on() {

        return command + "/on";
    }
    private static String off() {

        return command + "/off";
    }
}

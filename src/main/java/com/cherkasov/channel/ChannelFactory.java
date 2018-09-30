package com.cherkasov.channel;

public class ChannelFactory {
    public static Channel make(String channelDescription) {

        Channel channel;
        Destination destination = null;
        Channels channelEnum = Channels.EMPTY;

        String[] split = channelDescription.split("=");
        if (split.length >= 2) {
            channelEnum = Channels.valueOf(split[0].trim().toUpperCase());
            destination = new Destination();
            destination.setEndPoint(split[1]);
        }

        switch (channelEnum) {
            case SMS:
                channel = new SMSChannel(destination, 10, 10);
                break;
            case EMAIL:
                channel = new EmailChannel(destination, 10, 10);
                break;
            default:
                channel = new EmptyChannel();
        }

        return channel;
    }
}

package com.cherkasov.channel;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Configuration
public class ChannelFactory {

    @Bean
    @Scope("prototype")
    protected Channel make(String channelDescription) {

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

    @Bean
    public Function<String, Channel> beanFactory() {
        return this::make;
    }

}

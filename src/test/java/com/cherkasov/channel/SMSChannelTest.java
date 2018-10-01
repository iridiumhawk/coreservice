package com.cherkasov.channel;

import com.cherkasov.entities.ClientSubscription;
import com.cherkasov.entities.Event;
import org.junit.Test;

import static org.junit.Assert.*;

public class SMSChannelTest {

    @Test
    public void fire() {

        Channel channel = ChannelFactory.make("sms=+79199550151");

        channel.fire(getEvent(), getSubscription());
    }

    private Event getEvent() {

        Event event = new Event("controller","device", "sensor", "", 0L);

        return event;
    }

    private ClientSubscription getSubscription() {

        ClientSubscription clientSubscription = new ClientSubscription();
        clientSubscription.setMessage("test message only");
        return clientSubscription;
    }
}
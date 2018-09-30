package com.cherkasov.entities;

import com.cherkasov.channel.Channel;

public class EventNotification implements Runnable {
    private final Channel channel;
    private final Event event;
    private final ClientSubscription clientSubscription;

    public EventNotification(Channel channel, Event event, ClientSubscription clientSubscription) {

        this.channel = channel;
        this.event = event;
        this.clientSubscription = clientSubscription;
    }

    @Override
    public void run() {

        channel.fire(event, clientSubscription);
    }
}

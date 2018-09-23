package com.cherkasov.channel;

import com.cherkasov.entities.ClientSubscription;
import com.cherkasov.entities.Event;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EmptyChannel extends AbstractChannel implements Channel {

    @Override
    public void fire(Event event, ClientSubscription subscription) {

        String message = subscription.getMessage();
        if (message != null) {
            message = message.trim() + " Value=" + event.getValue();
        } else {
            message = "Value=" + event.getValue();
        }
        log.info(message);
    }
}

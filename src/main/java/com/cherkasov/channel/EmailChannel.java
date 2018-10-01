package com.cherkasov.channel;

import com.cherkasov.entities.ClientSubscription;
import com.cherkasov.entities.Event;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.text.MessageFormat;

public class EmailChannel extends AbstractChannel implements Channel {

    private String server = "";

    public EmailChannel(Destination destination, Integer timeOut, Integer retrieveCount) {

        super(destination, timeOut, retrieveCount);
    }

    @Override
    public String fire(final Event event, final ClientSubscription subscription) {

        String message = subscription.getMessage();
        if (message != null) {
            message = message.trim() + " Value=" + event.getValue();
        } else {
            message = "Value=" + event.getValue();
        }

        // TODO: 23.09.2018 send mail
        //use mail service
        return "ok";
    }
}

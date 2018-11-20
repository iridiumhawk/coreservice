package com.cherkasov.channel;

import com.cherkasov.entities.ClientSubscription;
import com.cherkasov.entities.Event;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@Slf4j
public class EmailChannel extends AbstractChannel implements Channel {

    @Autowired
    private JavaMailSender EMAIL_SENDER;

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

        sendSimpleMessage(destination.getEndPoint().trim(), "event", message);
        return "ok";
    }

    private void sendSimpleMessage(String to, String subject, String text) {
        log.debug("Sending mail");
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        EMAIL_SENDER.send(message);
    }
}

package com.cherkasov.channel;

import com.cherkasov.entities.ClientSubscription;
import com.cherkasov.entities.Event;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.text.MessageFormat;

@Slf4j
public class SMSChannel extends AbstractChannel implements Channel {

    // TODO: 22.09.2018 set in properties file
    private String apiKey = "1f587ef2-cf08-4584-7968-28b498643bb5";
    private String pattern = "https://sms.ru/sms/send?api_id={0}&to={1}&text={2}&json=1";

    //https://sms.ru/sms/send?api_id=1f587ef2-cf08-4584-7968-28b498643bb5&to=79199550151,74993221627&msg=hello+world&json=1


    public SMSChannel(Destination destination, Integer timeOut, Integer retrieveCount) {

        super(destination, timeOut, retrieveCount);
    }

    @Override
    public String fire(final Event event, final ClientSubscription subscription) {

        String message = subscription.getMessage();
        if (message != null && event.getValue() != null) {
            message = message.trim() + ". value:" + event.getValue();
        } else {
            message = "value:" + event.getValue();
        }

        message = message.replace(" ", "+");

        log.debug("SMS channel message: {}", message);

        RestTemplate restTemplate = new RestTemplate();
        String endPoint = destination.getEndPoint().trim().replace("+", "");
//        .replaceFirst("8", "7");
        String resourceUrl = MessageFormat.format(pattern, apiKey, endPoint, message);
//        log.debug("SMS resourceUrl: {}", resourceUrl);
        // TODO: 01.10.2018 on sending replaced all the "+" by encoding sing
        ResponseEntity<String> response = restTemplate.getForEntity(resourceUrl, String.class);
        log.debug("SMS channel response: {}", response);
        return response.toString();
    }
}

package com.cherkasov.channel;

import com.cherkasov.entities.ClientSubscription;
import com.cherkasov.entities.Event;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.text.MessageFormat;

@Slf4j
public class SMSChannel extends AbstractChannel implements Channel {
    //https://sms.ru/sms/send?api_id=1f587ef2-cf08-4584-7968-28b498643bb5&to=79199550151,74993221627&msg=hello+world&json=1

    @Value("${channels.sms.apikey}")
    private String API_KEY_SMS;
    @Value("${channels.sms.pattern}")
    private String PATTERN_SMS;


    public SMSChannel(Destination destination, Integer timeOut, Integer retrieveCount) {

        super(destination, timeOut, retrieveCount);
    }

    @Override
    public String fire(final Event event, final ClientSubscription subscription) {

        StringBuilder message = new StringBuilder();
        message.append("\"").append(subscription.getMessage());

        if (subscription.getMessage() != null && event.getValue() != null) {
            message.append(". value: ").append(event.getValue());
        } else {
            message.append("value: ").append(event.getValue());
        }
        message.append("\"");

        String sendMessage = message.toString();//.replaceAll(" ", "+");

        log.debug("SMS channel message: {}", sendMessage);

        RestTemplate restTemplate = new RestTemplate();
        String endPoint = destination.getEndPoint().trim().replaceFirst("\\+", "");
//        .replaceFirst("8", "7");
        String resourceUrl = MessageFormat.format(PATTERN_SMS, API_KEY_SMS, endPoint, sendMessage);
        log.debug("SMS resourceUrl: {}", resourceUrl);
        ResponseEntity<String> response = restTemplate.getForEntity(resourceUrl, String.class);
        log.debug("SMS channel response: {}", response);
        return response.toString();
    }
}

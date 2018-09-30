package com.cherkasov.channel;

import com.cherkasov.entities.ClientSubscription;
import com.cherkasov.entities.Event;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.text.MessageFormat;

public class SMSChannel extends AbstractChannel implements Channel {

    // TODO: 22.09.2018 set in properties file
    private String apiKey = "1f587ef2-cf08-4584-7968-28b498643bb5";
    private String pattern = "https://sms.ru/sms/send?{0}&to={1}&msg={2}&json=1";

    //https://sms.ru/sms/send?api_id=1f587ef2-cf08-4584-7968-28b498643bb5&to=79199550151,74993221627&msg=hello+world&json=1


    public SMSChannel(Destination destination, Integer timeOut, Integer retrieveCount) {

        super(destination, timeOut, retrieveCount);
    }

    @Override
    public void fire(final Event event, final ClientSubscription subscription) {

        String message = subscription.getMessage();
        if (message != null) {
            message = message.trim() + " Value=" + event.getValue();
        } else {
            message = "Value=" + event.getValue();
        }

        message = message.replace(" ", "+");

        RestTemplate restTemplate = new RestTemplate();
        String resourceUrl = MessageFormat.format(pattern, apiKey, destination.getEndPoint(), message);
        ResponseEntity<String> response = restTemplate.getForEntity(resourceUrl, String.class);
//        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    }
}

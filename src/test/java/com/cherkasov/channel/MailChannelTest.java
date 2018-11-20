package com.cherkasov.channel;

import com.cherkasov.entities.ClientSubscription;
import com.cherkasov.entities.Event;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.function.Function;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MailChannelTest {
    @Autowired
    private Function<String, Channel> beanFactory;

    @Test
    public void fire() {
        Channel channel = beanFactory.apply("email=hawk_san@mail.ru");
        channel.fire(getEvent(), getSubscription());
//        Channel channel = ChannelFactory.make("sms=+79199550151");
    }

    private Event getEvent() {

      return new Event("controller","device", "sensor", "", 0L);
    }

    private ClientSubscription getSubscription() {

        ClientSubscription clientSubscription = new ClientSubscription();
        clientSubscription.setMessage("test message only");
        return clientSubscription;
    }
}
package com.cherkasov.channel;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.function.Function;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ChannelFactoryTest {

    @Autowired
    private Function<String, Channel> beanFactory;

    @Test
    public void givenPrototypeInjection() {

        Channel firstInstance = beanFactory.apply("sms=+79199550151");
        Channel secondInstance = beanFactory.apply("sms=+79199550151");

        assertNotSame("New instance expected", firstInstance, secondInstance);
        assertThat("Objects are the same", firstInstance, instanceOf(SMSChannel.class));

    }

}
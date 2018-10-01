package com.cherkasov.channel;

import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.*;

public class ChannelFactoryTest {

    @Test
    public void make() {

        Channel sms = ChannelFactory.make("sms=+79199550151");
        Assert.assertThat(sms, instanceOf(SMSChannel.class));
    }
}
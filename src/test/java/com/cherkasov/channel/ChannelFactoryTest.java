package com.cherkasov.channel;

import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class ChannelFactoryTest {

    @Test
    public void make() {

        Channel sms = ChannelFactory.make("sms");
        Assert.assertThat(sms, is(Channels.SMS));
    }
}
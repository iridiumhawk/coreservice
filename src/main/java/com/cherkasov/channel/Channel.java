package com.cherkasov.channel;

import com.cherkasov.entities.ClientSubscription;
import com.cherkasov.entities.Event;

public interface Channel {
    void fire(Event event, ClientSubscription subscription);
}

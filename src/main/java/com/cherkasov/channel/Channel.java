package com.cherkasov.channel;

import com.cherkasov.entities.ClientSubscription;
import com.cherkasov.entities.Event;

public interface Channel {
    String fire(Event event, ClientSubscription subscription);
}

package com.cherkasov.repositories;

import com.cherkasov.entities.ClientSubscription;
import com.cherkasov.entities.Event;

import java.util.List;

public interface SubscribeCache {
    void add(ClientSubscription subscription);

    void update(ClientSubscription subscription);

    void delete(String controllerId, String deviceId);

    void deleteAll(String controllerId);

    List<ClientSubscription> get(Event event);
}

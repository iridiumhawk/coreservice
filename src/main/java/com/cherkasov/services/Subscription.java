package com.cherkasov.services;

import com.cherkasov.entities.ClientSubscription;
import com.cherkasov.entities.Event;

public interface Subscription {
    void addSubscription(ClientSubscription entity);
    void updateSubscription(ClientSubscription entity);
    void removeSubscriptionDevice(String controllerId, String deviceId);
    void removeAllSubscription(String controllerId);

    void fireEvent(Event event);
}

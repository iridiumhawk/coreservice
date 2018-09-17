package com.cherkasov.services;

public interface SubscribersStore {
    void addSubscription();
    void updateSubscription();
    void removeSubscription();
    void removeAllSubscription();
}

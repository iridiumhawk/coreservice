package com.cherkasov.services;

import com.cherkasov.entities.ClientSubscription;
import com.cherkasov.entities.Event;
import com.cherkasov.repositories.SubscribeCacheInMemory;
import com.cherkasov.repositories.SubscribeDAO;
import org.springframework.beans.factory.annotation.Autowired;

public class SubscriptionService implements Subscription {

  @Autowired
  private SubscribeDAO dao;

  @Autowired
  private SubscribeCacheInMemory cache;

  @Override
  public void addSubscription(ClientSubscription entity) {
    //dao.save
    //cache.add
  }

  @Override
  public void updateSubscription(ClientSubscription entity) {

  }

  @Override
  public void removeSubscriptionDevice(String controllerId, String deviceId) {

  }

  @Override
  public void removeAllSubscription(String controllerId) {

  }

  @Override
  public void fireEvent(Event event) {
    //check subscription and send message in canal
  }
}

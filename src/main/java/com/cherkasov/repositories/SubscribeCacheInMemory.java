package com.cherkasov.repositories;

import com.cherkasov.entities.ClientSubscription;
import java.util.HashMap;
import java.util.Map;

public class SubscribeCacheInMemory {
  //ControllerId, ClientSubscription
  private Map<String, ClientSubscription> cache = new HashMap<>();

  public SubscribeCacheInMemory() {
    //load subscription form DB
  }



}

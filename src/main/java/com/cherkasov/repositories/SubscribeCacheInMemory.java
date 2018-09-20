package com.cherkasov.repositories;

import com.cherkasov.entities.ClientSubscription;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SubscribeCacheInMemory {
  //ControllerId, ClientSubscription
  private Map<String, List<ClientSubscription>> cache = new ConcurrentHashMap<>();

  public SubscribeCacheInMemory() {
    //load subscription form DB
  }



}

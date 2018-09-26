package com.cherkasov.repositories;

import com.cherkasov.entities.ClientSubscription;
import com.cherkasov.entities.Event;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Slf4j
@Component
public class SubscribeCacheInMemory implements SubscribeCache {
    //ControllerId, ClientSubscription
    private Map<String, List<ClientSubscription>> cache = new ConcurrentHashMap<>();

    public SubscribeCacheInMemory() {
        //todo load subscription form DB on application start
    }

    @Override
    public void add(ClientSubscription subscription) {

        // TODO: 22.09.2018 make simple
        List<ClientSubscription> clientSubscriptions = cache.get(subscription.getControllerId());
        if (clientSubscriptions != null) {
            clientSubscriptions.add(subscription);
        } else {
            clientSubscriptions = new ArrayList<>();
            clientSubscriptions.add(subscription);
        }
        cache.put(subscription.getControllerId(), clientSubscriptions);
//        cache.compute(subscription.getControllerId(), subscription);
    }

    @Override
    public void update(ClientSubscription subscription) {

        List<ClientSubscription> clientSubscriptions = cache.get(subscription.getControllerId());
//        clientSubscriptions.stream().filter(cl -> cl.equals(subscription)).;
        for (ClientSubscription clientSubscription : clientSubscriptions) {
            if (clientSubscription.equals(subscription)) {
                //todo update value
            }
        }
    }

    @Override
    public void delete(String controllerId, String deviceId) {
        List<ClientSubscription> clientSubscriptions = cache.get(controllerId);
        List<ClientSubscription> collect = clientSubscriptions.stream().filter(cl -> cl.getDeviceId().equalsIgnoreCase(deviceId)).collect(Collectors.toList());
        clientSubscriptions.removeAll(collect);
    }

    @Override
    public void deleteAll(String controllerId) {
        cache.remove(controllerId);
    }

    @Override
    public List<ClientSubscription> get(Event event) {
        //returns subscription that coincidence with event
        List<ClientSubscription> clientSubscriptions = cache.get(event.getControllerId());
        List<ClientSubscription> collect = clientSubscriptions.stream().filter(cl -> cl.getDeviceId().equalsIgnoreCase(event.getDeviceId()) && cl.getSensorId().equalsIgnoreCase(event.getSensorId())).collect(Collectors.toList());
        // TODO: 22.09.2018 checking values

        return collect;
    }
}

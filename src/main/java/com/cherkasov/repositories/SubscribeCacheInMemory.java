package com.cherkasov.repositories;

import com.cherkasov.channel.Channel;
import com.cherkasov.channel.ChannelFactory;
import com.cherkasov.entities.ClientSubscription;
import com.cherkasov.entities.Event;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Slf4j
@Component
public class SubscribeCacheInMemory implements SubscribeCache {
    //ControllerId, ClientSubscription
    private final Map<String, List<ClientSubscription>> cache = new ConcurrentHashMap<>();


    // TODO: 22.09.2018 make more simple
    @Override
    public void add(ClientSubscription subscription) {

        subscription.setNotificationChannel(makeChannels(subscription));
        final List<ClientSubscription> clientSubscriptions = cache.get(subscription.getControllerId());
        if (clientSubscriptions != null) {
            clientSubscriptions.add(subscription);
            cache.put(subscription.getControllerId(), clientSubscriptions);
        } else {
            List<ClientSubscription> newClientSubscriptions = new ArrayList<>();
            newClientSubscriptions.add(subscription);
            cache.put(subscription.getControllerId(), newClientSubscriptions);
        }
//        cache.compute(subscription.getControllerId(), subscription);
    }

    // TODO: 01.10.2018 change algorithm for update
    @Override
    public void update(ClientSubscription subscription) {

        subscription.setNotificationChannel(makeChannels(subscription));
        final List<ClientSubscription> clientSubscriptions = cache.get(subscription.getControllerId());
        if (clientSubscriptions == null) {
            return;
        }
//        clientSubscriptions.stream().filter(cl -> cl.equals(subscription)).;
        synchronized (this) {
            for (int i = 0; i < clientSubscriptions.size(); i++) {
                if (clientSubscriptions.get(i).equals(subscription)) {
                    clientSubscriptions.set(i, subscription);
                }
            }
        }
    }

    @Override
    public void delete(String controllerId, String deviceId) {

        List<ClientSubscription> clientSubscriptions = cache.get(controllerId);
        if (clientSubscriptions == null) {
            return;
        }
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
        if (clientSubscriptions == null) {
            log.debug("subscriptions in cache is empty");
            return Collections.emptyList();
        }
        return clientSubscriptions.stream().filter(cl -> cl.getDeviceId().equalsIgnoreCase(event.getDeviceId()) && cl.getSensorId().equalsIgnoreCase(event.getSensorId())).collect(Collectors.toList());
        // TODO: 22.09.2018 checking values for alarm
    }

    private List<Channel> makeChannels(ClientSubscription entity) {

        List<Channel> channels = new ArrayList<>();
        for (String notification : entity.getNotifications()) {
            channels.add(ChannelFactory.make(notification));
        }
        return channels;
    }
}

package com.cherkasov.repositories;

import com.cherkasov.channel.Channel;
import com.cherkasov.entities.ClientSubscription;
import com.cherkasov.entities.Event;
import com.cherkasov.utils.SensorValueMatcher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

// TODO: 19.11.2018 1. limit subscriptions in memory, 2. algorithm for keeping in memory (frequency?) 3. fill cache on app start?

@Slf4j
@Component
public class SubscribeCacheInMemory implements SubscribeCache {

    @Autowired
    private Function<String, Channel> beanFactory;

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

    // TODO: 01.10.2018 change algorithm for update - this is ineffective
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
    public synchronized void delete(String controllerId, String deviceId) {

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
        log.debug("Number subscriptions in cache: {}", cache.size());
        //returns subscription that coincidence with event
        List<ClientSubscription> clientSubscriptions = cache.get(event.getControllerId());
        if (clientSubscriptions == null) {
            log.debug("subscriptions in cache is empty");
            return Collections.emptyList();
        }
        List<ClientSubscription> collect = clientSubscriptions.stream().filter(cl -> cl.getDeviceId().equalsIgnoreCase(event.getDeviceId()) && cl.getSensorId().equalsIgnoreCase(event.getSensorId())).collect(Collectors.toList());

        List<ClientSubscription> result = new ArrayList<>();
        for (ClientSubscription clientSubscription : collect) {
            for (String value : clientSubscription.getValue()) {
                // TODO: 19.11.2018 add complex checking
                if (new SensorValueMatcher<>(event.getValue(), value).equalMatch()) {
                    result.add(clientSubscription);
                }
            }
        }
        return result;
    }

    private List<Channel> makeChannels(ClientSubscription entity) {

        List<Channel> channels = new ArrayList<>();
        for (String notification : entity.getNotifications()) {
            channels.add(beanFactory.apply(notification));
//            channels.add(ChannelFactory.make(notification));
        }
        return channels;
    }
}

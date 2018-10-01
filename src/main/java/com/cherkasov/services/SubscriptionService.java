package com.cherkasov.services;

import com.cherkasov.channel.Channel;
import com.cherkasov.channel.ChannelFactory;
import com.cherkasov.entities.ClientSubscription;
import com.cherkasov.entities.Event;
import com.cherkasov.repositories.SubscribeCacheInMemory;
import com.cherkasov.repositories.SubscribeDAOImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class SubscriptionService implements Subscription {

    @Autowired
    private SubscribeDAOImpl subscribeDAO;

    @Autowired
    private SubscribeCacheInMemory cache;

    @Autowired
    private NotificationService notificationService;

    private String collection = "subscription"; // TODO: 28.09.2018 ??? WTF, change name or Database

    @Override
    public List<ClientSubscription> viewSubscription() {
        return subscribeDAO.getAll(collection);
    }

    @Override
    public void addSubscription(ClientSubscription entity) {
        //subscribeDAO.save
        subscribeDAO.insert(entity, collection);

        //cache.add
        cache.add(entity);
    }

    @Override
    public void updateSubscription(ClientSubscription entity) {

        subscribeDAO.update("controllerid", entity.getControllerId(), entity, collection);

        cache.update(entity);
    }

    @Override
    public void removeSubscriptionDevice(String controllerId, String deviceId) {

        // TODO: 28.09.2018 ??? removes all devices
        subscribeDAO.deleteByField("deviceid", deviceId, collection);
        cache.delete(controllerId, deviceId);
    }

    @Override
    public void removeAllSubscription(String controllerId) {

        subscribeDAO.deleteByField("controllerid", controllerId, collection);
        cache.deleteAll(controllerId);
    }

    @Override
    public void fireEvent(Event event) {
        //check subscription and send message in channel
        List<ClientSubscription> clientSubscriptions = cache.get(event);
        if (clientSubscriptions.isEmpty()) {
            //check subscription in db
            List<ClientSubscription> all = subscribeDAO.getAll(event.getControllerId());
            if (all == null || all.isEmpty()) {
                return;
            }
            all.forEach(s -> cache.add(s));
            clientSubscriptions = cache.get(event);
        }
        notificationService.send(event, clientSubscriptions);
    }
}

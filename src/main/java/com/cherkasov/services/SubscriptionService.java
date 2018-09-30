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

    private String collection = "subscription"; // TODO: 28.09.2018 ??? WTF

    @Override
    public void addSubscription(ClientSubscription entity) {
        //subscribeDAO.save
        subscribeDAO.insert(entity, collection);

        //cache.add
        entity.setNotificationChannel(makeChannels(entity));
        cache.add(entity);
    }

    @Override
    public void updateSubscription(ClientSubscription entity) {

        subscribeDAO.update("controllerid", entity.getControllerId(), entity, collection); // TODO: 28.09.2018 ???
        entity.setNotificationChannel(makeChannels(entity));
        cache.update(entity);
    }

    @Override
    public void removeSubscriptionDevice(String controllerId, String deviceId) {

        subscribeDAO.deleteByField("deviceid", deviceId, collection); // TODO: 28.09.2018 ??? removes all devices
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
        notificationService.send(event, clientSubscriptions);
    }

    private List<Channel> makeChannels(ClientSubscription entity) {

        List<Channel> channels = new ArrayList<>();
        for (String notification : entity.getNotifications()) {
            channels.add(ChannelFactory.make(notification));
        }
        return channels;
    }
}

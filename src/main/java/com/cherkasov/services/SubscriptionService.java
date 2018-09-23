package com.cherkasov.services;

import com.cherkasov.channel.Channel;
import com.cherkasov.channel.ChannelFactory;
import com.cherkasov.entities.ClientSubscription;
import com.cherkasov.entities.Event;
import com.cherkasov.entities.EventLog;
import com.cherkasov.repositories.SubscribeCacheInMemory;
import com.cherkasov.repositories.SubscribeDAO;
import com.cherkasov.repositories.SubscribeDAOImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class SubscriptionService implements Subscription {

    @Autowired
    private SubscribeDAOImpl dao;

    @Autowired
    private SubscribeCacheInMemory cache;

    private String collection = "subscription";

    @Override
    public void addSubscription(ClientSubscription entity) {
        //dao.save
        // TODO: 22.09.2018 make processing with database
        dao.insert(entity, collection);

        //cache.add
        entity.setNotificationChannel(makeChannels(entity));
        cache.add(entity);
    }

    @Override
    public void updateSubscription(ClientSubscription entity) {

        entity.setNotificationChannel(makeChannels(entity));
        cache.update(entity);
    }

    @Override
    public void removeSubscriptionDevice(String controllerId, String deviceId) {

        cache.delete(controllerId, deviceId);
    }

    @Override
    public void removeAllSubscription(String controllerId) {

        cache.deleteAll(controllerId);
    }

    @Override
    public void fireEvent(Event event) {
        // TODO: 22.09.2018 make parallel, now is synchronous

        //check subscription and send message in channel
        List<ClientSubscription> clientSubscriptions = cache.get(event);
        for (ClientSubscription clientSubscription : clientSubscriptions) {
            for (Channel channel : clientSubscription.getNotificationChannel()) {
                channel.fire(event, clientSubscription);
                // TODO: 23.09.2018 save log
                EventLog eventLog = new EventLog(event.getDeviceId(), event.getSensorId(), event.getValue(), event.getUpdateTime(), LocalDateTime.now().toString());
//                logDao.save(event.getControllerId(), eventLog);
            }
        }
    }

    private List<Channel> makeChannels(ClientSubscription entity) {

        List<Channel> channels = new ArrayList<>();
        for (String notification : entity.getNotifications()) {
            channels.add(ChannelFactory.make(notification));
        }
        return channels;
    }
}

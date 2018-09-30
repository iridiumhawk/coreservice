package com.cherkasov.services;

import com.cherkasov.channel.Channel;
import com.cherkasov.entities.ClientSubscription;
import com.cherkasov.entities.Event;
import com.cherkasov.entities.EventLog;
import com.cherkasov.entities.EventNotification;
import com.cherkasov.repositories.EventLogDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.*;

@Slf4j
@Service
public class NotificationService implements AutoCloseable{
    private final int minSize = 2;
    private final int maxSize = 8;
    private final int keepAliveTime = 1000;
    private BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>();
    private ExecutorService pool;

    @Autowired
    private EventLogDAO logDAO;

    public NotificationService() {

        pool = new ThreadPoolExecutor(minSize, maxSize, keepAliveTime, TimeUnit.MILLISECONDS, queue);
    }

    public void send(Event event, List<ClientSubscription> clientSubscriptions) {
        for (ClientSubscription clientSubscription : clientSubscriptions) {
            for (Channel channel : clientSubscription.getNotificationChannel()) {
                pool.execute(new EventNotification(channel, event, clientSubscription));
                logDAO.save(new EventLog(event), event.getControllerId());
                log.trace("Event fired: {}", event);
            }
        }
    }

    @Override
    public void close() throws Exception {
        pool.shutdown();
    }
}

package com.cherkasov.repositories;

import com.cherkasov.entities.EventLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class EventLogDAO extends AbstractDAO<EventLog> {

    public EventLogDAO(MongoOperations operations) {

        super(operations);
    }

    @Override
    public Class<EventLog> getGenericType() {

        return EventLog.class;
    }
}

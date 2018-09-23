package com.cherkasov.repositories;

import com.cherkasov.entities.EventLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class EventLogDAO extends AbstractDAO<EventLog> {
    @Override
    public Class<EventLog> getGenericType() {

        return EventLog.class;
    }
}

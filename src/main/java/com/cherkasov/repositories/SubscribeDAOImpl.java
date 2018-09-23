package com.cherkasov.repositories;

import com.cherkasov.entities.ClientSubscription;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class SubscribeDAOImpl extends AbstractDAO<ClientSubscription> {

    @Override
    public Class<ClientSubscription> getGenericType() {

        return ClientSubscription.class;
    }
}

package com.cherkasov.entities;

import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@NoArgsConstructor
public class EmptyClientSubscription extends ClientSubscription {
    public EmptyClientSubscription(String controllerId) {
        this.controllerId = controllerId;
    }

}

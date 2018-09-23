package com.cherkasov.channel;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class AbstractChannel {
    protected Destination destination;
    protected Integer timeOut;
    protected Integer retrieveCount;

}

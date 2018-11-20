package com.cherkasov.utils;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SensorValueMatcher<F, S> {

    private F first;
    private S second;

    public boolean equalMatch() {

        return first.equals(second);
    }
}

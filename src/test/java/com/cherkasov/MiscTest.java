package com.cherkasov;

import com.cherkasov.entities.Device;
import com.mongodb.util.JSON;
import org.junit.Test;

public class MiscTest {
    @Test
    public void serialize() {
        JSON.serialize(new Device());
    }
}

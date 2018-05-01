package com.cherkasov.repositories;

import com.cherkasov.entities.Device;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

@Deprecated
public interface DeviceRepository extends MongoRepository<Device, String>{
//    @Query("{'id':?0}")
    public List<Device> findByDeviceId(String id);
}

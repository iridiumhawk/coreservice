package com.cherkasov.repositories;

import com.cherkasov.entities.Device;
import com.mongodb.WriteResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
@Slf4j
public class DeviceDAOImpl implements DeviceDAO {
    @Autowired
    private MongoOperations operations;

    private static final String DEVICE_COLLECTION = "devices";

    public DeviceDAOImpl(MongoOperations operations) {

        this.operations = operations;
    }

    public DeviceDAOImpl() {

    }


    @Override
    public void insert(Device device) {
    this.operations.insert(device, DEVICE_COLLECTION);
    }

    @Override
    public void update(Device device) {
        this.operations.save(device, DEVICE_COLLECTION);
    }

    @Override
    public Device findByName(String device) {

        Query query = new Query(Criteria.where("name").is(device));
        return this.operations.findOne(query, Device.class, DEVICE_COLLECTION);
    }

    @Override
    public int deleteByName(String device) {
        Query query = new Query(Criteria.where("name").is(device));
        WriteResult result = this.operations.remove(query, Device.class, DEVICE_COLLECTION);
        return result.getN();
    }

    @Override
    public void insertAll(List<Device> devices) {
        this.operations.insert(devices, DEVICE_COLLECTION);
    }

    @Override
    public boolean dropCollectionIfExist() {

        if (operations.collectionExists(DEVICE_COLLECTION)) {
            operations.dropCollection(DEVICE_COLLECTION);
            log.info("Collection dropped");
            return true;
        }
        return false;
    }
}

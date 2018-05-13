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

//    private static final String DEVICE_COLLECTION = "devices";

    public DeviceDAOImpl(MongoOperations operations) {

        this.operations = operations;
    }

    public DeviceDAOImpl() {

    }


    @Override
    public void insert(Device device, String collection) {
    this.operations.insert(device, collection);
    }

    @Override
    public void update(Device device, String collection) {
        this.operations.save(device, collection);
    }

    @Override
    public Device findByName(String device, String collection) {

        Query query = new Query(Criteria.where("name").is(device));
        return this.operations.findOne(query, Device.class, collection);
    }

    @Override
    public List<Device> getAll(String collection) {

        return this.operations.findAll(Device.class, collection);
    }


    @Override
    public int deleteByName(String device, String collection) {
        Query query = new Query(Criteria.where("name").is(device));
        WriteResult result = this.operations.remove(query, Device.class, collection);
        return result.getN();
    }

    @Override
    public void insertAll(List<Device> devices, String collection) {
        this.operations.insert(devices, collection);
    }

    @Override
    public boolean dropCollectionIfExist(String collection) {

        if (operations.collectionExists(collection)) {
            operations.dropCollection(collection);
            log.info("Collection dropped");
            return true;
        }
        return false;
    }
}

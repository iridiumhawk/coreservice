package com.cherkasov.repositories;

import com.cherkasov.entities.Device;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;

import java.util.Set;

import com.mongodb.util.JSON;
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
        // TODO: 26.05.2018 make more simple
        Device byName = findByName(device.getName(), collection);
        if (byName != null) {
            device.setId(byName.getId());
            this.operations.save(device, collection);
        } else {
            insert(device, collection);
        }
    }

    @Override
    public void updateAll(List<Device> devices, String collection) {

        for (Device deviceUpdate : devices) {
            update(deviceUpdate, collection);
        }
    }

    @Override
    public Device findByName(String device, String collection) {

        Query query = new Query(Criteria.where("name").is(device));
        return this.operations.findOne(query, Device.class, collection);
    }

    // TODO: 27.05.2018 while searching dont take class name in consideration !!!  simple return result as this class
    @Override
    public List<Device> getAll(String collection) {
        Query query = new Query(Criteria.where("_class").is("com.cherkasov.entities.Device"));
        List<Device> all = this.operations.find(query, Device.class, collection);
//        return this.operations.findAll(Device.class, collection);

        return all;
    }

    @Override
    public int deleteByName(String device, String collection) {

        Query query = new Query(Criteria.where("name").is(device));
        WriteResult result = this.operations.remove(query, Device.class, collection);
        return result.getN();
    }

    @Override
    public List<Device> deleteAll(String collection) {

        Query query = new Query(Criteria.where("_class").is("com.cherkasov.entities.Device"));
        List<Device> remove = this.operations.findAllAndRemove(query, Device.class, collection);
        log.trace("Removed devices:\n{}", remove);
        return remove;

/*        List<Device> all = this.operations.findAll(Device.class, collection);
        for (Device device : all) {
            WriteResult remove = this.operations.remove(device);
        }
        log.trace("Removed devices:\n{}", all);
        return all;*/
    }

    @Override
    public List<Device> deleteAllNull(String collection) {
        // TODO: 26.05.2018 null is not correct criteria !!! removes all data
        //try "_class": "com.cherkasov.entities.Device"
        Query query = new Query(Criteria.where("name").is(null).andOperator(Criteria.where("_class").is("com.cherkasov.entities.Device")));
        List<Device> remove = this.operations.findAllAndRemove(query, Device.class, collection);
        log.trace("Removed devices:\n{}", remove);
        return remove;
    }

    @Override
    public void insertJson(String json, String collection) {

        DBObject dbObject = (DBObject) JSON.parse(json);

        this.operations.insert(dbObject, collection);
    }

    @Override
    public void insertAll(List<Device> devices, String collection) {

        for (Device device : devices) {
            this.operations.save(device, collection);
        }
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

    @Override
    public Set<String> getAllControllersName() {

        return operations.getCollectionNames();
    }
}

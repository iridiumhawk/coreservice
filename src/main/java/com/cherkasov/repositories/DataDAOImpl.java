package com.cherkasov.repositories;

import com.cherkasov.entities.TimeSeriesData;
import com.mongodb.WriteResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Repository
@Slf4j
public class DataDAOImpl implements DataDAO {

    @Autowired
    private MongoOperations operations;

    @Override
    public void insert(TimeSeriesData data, String collection) {
        this.operations.insert(data, collection);

    }

    @Override
    public void insertAll(List<TimeSeriesData> data, String collection) {
        this.operations.insert(data, collection);
    }

    @Override
    public List<TimeSeriesData> findAllByDeviceId(String deviceId, String collection) {
        Query query = new Query(Criteria.where("deviceId").is(deviceId));

        return this.operations.find(query, TimeSeriesData.class, collection);
    }

    @Override
    public TimeSeriesData findLastByDeviceId(String deviceId, String collection) {

        long epochSecond = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);

        //todo need correct clause, how to get last value?
        Query query = new Query(Criteria.where("deviceId").is(deviceId).and("updateTime").gte(epochSecond));
        return this.operations.findOne(query, TimeSeriesData.class, collection);
    }

    @Override
    public int deleteByDeviceId(String deviceId, String collection) {
        Query query = new Query(Criteria.where("deviceId").is(deviceId));
        WriteResult result = this.operations.remove(query, TimeSeriesData.class, collection);
        return result.getN();
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

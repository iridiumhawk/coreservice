package com.cherkasov.repositories;

import com.cherkasov.entities.TimeSeriesData;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;
import com.mongodb.util.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Slf4j
@Repository
public class DataDAOImpl implements DataDAO {

    @Autowired
    private MongoOperations operations;

    @Override
    public void insert(TimeSeriesData data, String collection) {

        this.operations.insert(data, collection);
    }

    @Override
    public void insertJson(String json, String collection) {

        DBObject dbObject = (DBObject) JSON.parse(json);

        this.operations.insert(dbObject, collection);
    }

    @Override
    public void insertAll(List<TimeSeriesData> data, String collection) {

        this.operations.insert(data, collection);
    }

    @Override
    public List<TimeSeriesData> findAllByDeviceId(String deviceId, String collection) {

        Query query = new Query(Criteria.where("deviceid").is(deviceId));
        query.with(new Sort(Sort.Direction.DESC, "updatetime"));

        return this.operations.find(query, TimeSeriesData.class, collection);
    }

    @Override
    public TimeSeriesData findLastByDeviceId(String deviceId, String collection) {

//        long epochSecond = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);

        Query query = new Query(Criteria.where("deviceid").is(deviceId));
//        query.limit(1);
        query.with(new Sort(Sort.Direction.DESC, "updatetime"));
        return this.operations.findOne(query, TimeSeriesData.class, collection);
    }

    @Override
    public int deleteByDeviceId(String deviceId, String collection) {

        Query query = new Query(Criteria.where("deviceid").is(deviceId));
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

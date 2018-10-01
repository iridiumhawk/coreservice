package com.cherkasov.repositories;

import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;
import com.mongodb.util.JSON;

import java.util.List;
import java.util.Set;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public abstract class AbstractDAO<T> {

    @Autowired
    private MongoOperations operations;

    public AbstractDAO() {

    }

    public AbstractDAO(MongoOperations operations) {

        this.operations = operations;
    }

    public abstract Class<T> getGenericType();

    public void insert(T value, String collection) {
        this.operations.insert(value, collection);
    }

    public void save(T value, String collection) {
        this.operations.save(value, collection);
    }


    // TODO: 27.09.2018 field _class is absent while JSON parsing
    public T update(String fieldName, String fieldValue, T entity, String collection) {
        Gson gson = new Gson();
        DBObject newEntity = BasicDBObject.parse(gson.toJson(entity));
        log.debug("DBObject: {}", newEntity);
        Update update = Update.fromDBObject(newEntity);
        log.debug("Update: {}", update);

        Query query = new Query(Criteria.where(fieldName).is(fieldValue).andOperator(Criteria.where("_class").is(getGenericType().getName())));
         return (T)this.operations.findAndModify(query, update, entity.getClass(), collection);
//       return this.operations.updateFirst(query, update, entity.getClass(), collection);
//       return this.operations.save(entity, collection);

    }

    public void updateAll(String fieldName, String fieldValue, List<T> values, String collection) {

        for (T valueToUpdate : values) {
            update(fieldName, fieldValue, valueToUpdate, collection);
        }
    }

    public T findByField(String fieldName, String fieldValue, String collection) {

        Query query = new Query(Criteria.where(fieldName).is(fieldValue));
        return this.operations.findOne(query, getGenericType(), collection);
    }

    public List<T> getAll(String collection) {

        Query query = new Query(Criteria.where("_class").is(getGenericType().getName()));
        return this.operations.find(query, getGenericType(), collection);
    }

    public List<T> getAllByField(String fieldName, String fieldValue, String collection) {
        Query query = new Query(Criteria.where(fieldName).is(fieldValue).andOperator(Criteria.where("_class").is(getGenericType().getName())));
        return this.operations.find(query, getGenericType(), collection);
    }

    public int deleteByField(String fieldName, String fieldValue, String collection) {

        Query query = new Query(Criteria.where(fieldName).is(fieldValue));
        WriteResult result = this.operations.remove(query, getGenericType(), collection);
        return result.getN();
    }

    public List<T> deleteAll(String collection) {

        Query query = new Query(Criteria.where("_class").is(getGenericType().getName()));
        List<T> remove = this.operations.findAllAndRemove(query, getGenericType(), collection);
        log.trace("Removed devices:\n{}", remove);
        return remove;
    }

    public List<T> deleteAllNullbyField(String fieldName, String collection) {

        Query query = new Query(Criteria.where(fieldName).is(null).andOperator(Criteria.where("_class").is(getGenericType().getName())));
        List<T> remove = this.operations.findAllAndRemove(query, getGenericType(), collection);
        log.trace("Removed devices:\n{}", remove);
        return remove;
    }

    public void insertJson(String json, String collection) {

        DBObject dbObject = (DBObject) JSON.parse(json);
        this.operations.insert(dbObject, collection);
    }

    public void insertAll(List<T> values, String collection) {
//        this.operations.insertAll();
        for (T value : values) {
            this.operations.save(value, collection);
        }
    }

    public boolean dropCollectionIfExist(String collection) {

        if (operations.collectionExists(collection)) {
            operations.dropCollection(collection);
            log.info("Collection dropped");
            return true;
        }
        return false;
    }

    public Set<String> getAllControllersName() {

        return operations.getCollectionNames();
    }
}

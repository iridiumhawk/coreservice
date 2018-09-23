package com.cherkasov.repositories;

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
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public abstract class AbstractDAO<T> {

    @Autowired
    private MongoOperations operations;

    public AbstractDAO() {

    }

    public abstract Class<T> getGenericType();

    public void insert(T value, String collection) {
        this.operations.insert(value, collection);
    }

    // TODO: 26.05.2018 make more simple
    public void update(T entity, String collection) {
        this.operations.save(entity, collection);
//        T byName = findByName(entity.getName(), collection);
//        if (byName != null) {
//            entity.setId(byName.getId());
//            this.operations.findAndModify();

      /*Query query6 = new Query();
		query6.addCriteria(Criteria.where("name").is("appleF"));

		Update update6 = new Update();
		update6.set("age", 101);
		update6.set("ic", 1111);

		//FindAndModifyOptions().returnNew(true) = newly updated document
		//FindAndModifyOptions().returnNew(false) = old document (not update yet)
		User userTest6 = mongoOperation.findAndModify(
				query6, update6,
				new FindAndModifyOptions().returnNew(true), User.class);*/
//            this.operations.save(entity, collection);
//        } else {
//            insert(entity, collection);
//        }
    }

    public void updateAll(List<T> values, String collection) {

        for (T valueToUpdate : values) {
            update(valueToUpdate, collection);
        }
    }

    public T findByField(String fieldName, String fieldValue, String collection) {

        Query query = new Query(Criteria.where(fieldName).is(fieldValue));
        return this.operations.findOne(query, getGenericType(), collection);
    }

    // TODO: 27.05.2018 while searching dont take class name in consideration !!!  simple return result as this class

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

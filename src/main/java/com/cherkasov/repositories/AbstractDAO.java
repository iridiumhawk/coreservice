package com.cherkasov.repositories;

import com.cherkasov.entities.Device;
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
public class AbstractDAO<T> {

  private String entityClassName; // = "com.cherkasov.entities.Device"
  @Autowired
  private MongoOperations operations;
  private Class<?> entityClass;

  void setEntityClassName(String className) {
    this.entityClassName = className;
  }

  void setClass(Class<?> clazz) {
    this.entityClass = clazz;
  }

  public void insert(T device, String collection) {
    this.operations.insert(device, collection);
  }

  // TODO: 26.05.2018 make more simple
  public void update(T entity, String collection) {
    T byName = findByName(entity.getName(), collection);
    if (byName != null) {
      entity.setId(byName.getId());
      this.operations.save(entity, collection);
    } else {
      insert(entity, collection);
    }
  }

  public void updateAll(List<T> devices, String collection) {
    for (T deviceUpdate : devices) {
      update(deviceUpdate, collection);
    }
  }

  public T findByName(String entity, String collection, Class<T> clazz) {
    Query query = new Query(Criteria.where("name").is(entity));
    return this.operations.findOne(query, clazz, collection);
  }

  // TODO: 27.05.2018 while searching dont take class name in consideration !!!  simple return result as this class

  public List<T> getAll(String collection, Class<T> clazz) {
    Query query = new Query(Criteria.where("_class").is(entityClassName));
    List<T> all = this.operations.find(query, clazz, collection);
    return all;
  }

  public int deleteByName(String device, String collection, Class<T> clazz) {
    Query query = new Query(Criteria.where("name").is(device));
    WriteResult result = this.operations.remove(query, clazz, collection);
    return result.getN();
  }


  public List<T> deleteAll(String collection, Class<T> clazz) {
    Query query = new Query(Criteria.where("_class").is(entityClassName));
    List<T> remove = this.operations.findAllAndRemove(query, clazz, collection);
    log.trace("Removed devices:\n{}", remove);
    return remove;
  }

  public List<T> deleteAllNull(String collection, Class<T> clazz) {
    Query query = new Query(Criteria.where("name").is(null).andOperator(Criteria.where("_class").is(
        entityClassName)));
    List<T> remove = this.operations.findAllAndRemove(query, clazz, collection);
    log.trace("Removed devices:\n{}", remove);
    return remove;
  }

  public void insertJson(String json, String collection) {
    DBObject dbObject = (DBObject) JSON.parse(json);
    this.operations.insert(dbObject, collection);
  }

  public void insertAll(List<T> devices, String collection) {
    for (T device : devices) {
      this.operations.save(device, collection);
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

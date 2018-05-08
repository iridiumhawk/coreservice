package com.cherkasov.repositories;

import com.cherkasov.entities.ClientReference;
import com.cherkasov.entities.Credential;
import com.cherkasov.entities.User;
import com.cherkasov.exceptions.DataBaseException;
import java.util.List;
import java.util.Objects;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Repository for database.
 *
 */
@Slf4j
@Repository
@Transactional(readOnly = true)
public class DataRepository {

  @PersistenceContext
  private EntityManager em;

  @Transactional
  public ClientReference saveEntity(ClientReference data) {
    Objects.requireNonNull(data, "Cannot store entity because it is null");
//    data.setId(null);
    try {
    if (data.getId() == null) {
      log.trace("Persist request for alias: {}", data.getApiKey());
      em.persist(data);
      return data;
    } else {
      log.trace("Update request for alias: {}", data.getApiKey());
      return em.merge(data);
    }

    } catch (NullPointerException e) {
      log.error("EntityManager instance is not set");
      throw new DataBaseException(e.getMessage());
    }
  }

  @Transactional
  public Credential saveCredential(Credential data) {
    Objects.requireNonNull(data, "Cannot store entity because it is null");
//    data.setId(null);
    try {
    if (data.getId() == null) {
      log.trace("Persist request for alias: {}", data.getEntityId());
      em.persist(data);
      return data;
    } else {
      log.trace("Update request for alias: {}", data.getEntityId());
      return em.merge(data);
    }

    } catch (NullPointerException e) {
      log.error("EntityManager instance is not set");
      throw new DataBaseException(e.getMessage());
    }
  }

  public List<Credential> getByEntityId(String id) {
    log.trace("Get entity by id={}", id);
    List<Credential> credential;
    try {
      credential = em.createNamedQuery("Credential.getById", Credential.class)
              .setParameter("apikey", id)
              .getResultList();
//              .getSingleResult();
    } catch (NoResultException | NonUniqueResultException e) {
      return null;
    }
    return credential;
  }


  /**
   * Revert null object into "null" string
   * @param fieldName
   * @return
   */
  private String revertNullIntoString(Object fieldName) {
    if (fieldName == null) {
      return "null";
    }
    return fieldName.toString();
  }


  public List<ClientReference> getAllEntity() {
    log.trace("Loading all data from database");
    return em.createNamedQuery("Store.getAll", ClientReference.class).getResultList();
  }

  /**
   * Only for test
   */
  public ClientReference getById(Integer id) {
    log.trace("Get entity by id={}", id);
    return em.find(ClientReference.class, id);
  }



  @Transactional
  public boolean removeById(Integer id) {
    log.trace("Trying to remove entity from database with id={}", id);
    return em.createNamedQuery("Store.removeById").setParameter("id", id).executeUpdate() != 0;
  }


  @Transactional
  public User saveUser(User user) {
    em.persist(user);
    return user;
  }

  public User getByApiKey(String apiKey) {
    final User user;
    try {
      user = em.createNamedQuery("User.getByAPI", User.class)
          .setParameter("apikey", apiKey)
          .getSingleResult();
    } catch (NoResultException e) {
      return User.emptyUser();
    }
    return user;
  }

  public ClientReference getByHost(String host) {
    final ClientReference client;
    try {
      client = em.createNamedQuery("Store.getByHost", ClientReference.class)
          .setParameter("host", host)
          .getSingleResult();
    } catch (NoResultException e) {
      return null;
    }
    return client;
  }
}

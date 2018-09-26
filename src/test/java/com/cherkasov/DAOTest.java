package com.cherkasov;

import com.cherkasov.entities.Device;
import com.cherkasov.entities.EventLog;
import com.cherkasov.repositories.EventLogDAO;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;
import com.mongodb.util.JSON;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DAOTest {

    @Autowired
    private EventLogDAO logDAO;

    private String collection = "testcollection";

    @Before
    public void before() {
        logDAO.insert(getEntity(), collection);
    }

    @After
    public void after() {
        logDAO.dropCollectionIfExist(collection);
    }

    @Test
    public void insert() {
        logDAO.insert(getEntity(), collection);
        List<EventLog> all = logDAO.getAll(collection);
        Assert.assertThat(all, hasSize(2));
    }

    @Test
    public void save() {
        logDAO.save(getEntity(), collection);
        List<EventLog> all = logDAO.getAll(collection);
        Assert.assertThat(all, hasSize(2));
    }

    @Test
    public void deleteByField() {
        logDAO.deleteByField("deviceId", "123", collection);
        List<EventLog> all = logDAO.getAll(collection);
        Assert.assertThat(all, hasSize(0));
    }

    @Test
    public void findByField() {

        EventLog eventLog = logDAO.findByField("deviceId", "123", collection);
        System.out.println(eventLog);
        Assert.assertThat(eventLog, is(equalTo(getEntity())));
    }

    @Test
    public void getAll() {
        List<EventLog> all = logDAO.getAll(collection);
        System.out.println(all);
        Assert.assertThat(all, hasSize(1));
    }

    @Test
    public void update() {

        System.out.println(logDAO.getAll(collection));
        EventLog entity = getEntity();
        entity.setValue("on");
        WriteResult result = logDAO.update("deviceId", "123", entity, collection);
        System.out.println(result);
        List<EventLog> all = logDAO.getAll(collection);
        System.out.println(all);
//        Assert.assertThat(deviceId.getDeviceId(), is("123"));
    }



    private EventLog getEntity() {

        EventLog eventLog = new EventLog();
        eventLog.setDeviceId("123");
        eventLog.setSensorId("345");
        eventLog.setValue("off");
        eventLog.setUpdateTime(5465767674L);
        eventLog.setSendEventTime("12:00:00T12.09.2018");
        return eventLog;
    }

    @Test
    public void json() {

        DBObject newEntity = BasicDBObject.parse(JSON.serialize(new Device()));
    }
}

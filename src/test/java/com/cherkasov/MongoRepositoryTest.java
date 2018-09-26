package com.cherkasov;

import com.cherkasov.entities.User;
import com.cherkasov.repositories.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MongoRepositoryTest {

    @Autowired
    private UserRepository repository;

    @Test
    public void userSave() {

        repository.save(getUser());
        List<User> all = repository.findAll();
        System.out.println(all);
        Assert.assertThat(all, hasSize(1));
    }

    @Test
    public void userFind() {

        User user = getUser();
//        user.setLogin("new");
        User one = repository.findOne(Example.of(user));
        System.out.println(one);
        Assert.assertThat(one.getEmail(), is("a@b.com"));
    }

    private User getUser() {

        User user = new User();
        user.setEmail("a@b.com");
        user.setLogin("user");
        user.setPassword("pass");
        return user;
    }
}

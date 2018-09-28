package com.cherkasov.services;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@SpringBootTest
@RunWith(SpringRunner.class)
public class EmailServiceTest {

  @Autowired
  private EmailService service;

  @Test
  public void test() {
    service.sendSimpleMessage("test@mail.ru", "event", "event fired");
  }
}
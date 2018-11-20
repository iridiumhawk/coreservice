package com.cherkasov.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Deprecated
public class EmailService {

    @Autowired
    public  JavaMailSender EMAIL_SENDER;
}

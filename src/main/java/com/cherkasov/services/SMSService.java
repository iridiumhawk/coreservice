package com.cherkasov.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Deprecated
public class SMSService {

    @Value("${channels.sms.apikey}")
    public String API_KEY_SMS;
    @Value("${channels.sms.pattern}")
    public String PATTERN_SMS;

}

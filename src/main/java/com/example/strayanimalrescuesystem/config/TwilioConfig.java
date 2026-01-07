package com.example.strayanimalrescuesystem.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import com.twilio.Twilio;

import javax.annotation.PostConstruct;

@Configuration
public class TwilioConfig {

    @Value("${TWILIO_ACCOUNT_SID}")
    private String accountSid;

    @Value("${TWILIO_AUTH_TOKEN}")
    private String authToken;

    @Value("${TWILIO_PHONE}")
    private String twilioPhoneNumber;

    @PostConstruct
    public void init() {
        Twilio.init(accountSid, authToken);
        System.out.println("Twilio initialized successfully!");
    }

    public String getTwilioPhoneNumber() {
        return twilioPhoneNumber;
    }
}

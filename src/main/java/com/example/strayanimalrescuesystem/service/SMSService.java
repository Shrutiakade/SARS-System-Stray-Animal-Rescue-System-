package com.example.strayanimalrescuesystem.service;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.strayanimalrescuesystem.config.TwilioConfig;

@Service
public class SMSService {

    @Autowired
    private TwilioConfig twilioConfig;

    public String sendSMS(String to, String messageBody) {
        try {
            Message message = Message.creator(
                            new PhoneNumber(to), // Receiver's phone number
                            new PhoneNumber(twilioConfig.getTwilioPhoneNumber()), // Twilio phone number
                            messageBody)
                    .create();

            return "Message sent successfully! SID: " + message.getSid();
        } catch (Exception e) {
            return "Failed to send message: " + e.getMessage();
        }
    }
}
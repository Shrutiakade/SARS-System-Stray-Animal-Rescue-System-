package com.example.strayanimalrescuesystem.controller;

import com.example.strayanimalrescuesystem.service.SMSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SMSController {

    @Autowired
    private SMSService smsService;

    @GetMapping("/sendSMS")
    public String sendSMS(@RequestParam String to, @RequestParam String message) {
        return smsService.sendSMS(to, message);
    }
}

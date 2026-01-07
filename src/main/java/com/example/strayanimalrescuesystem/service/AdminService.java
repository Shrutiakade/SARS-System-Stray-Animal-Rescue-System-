package com.example.strayanimalrescuesystem.service;

import com.example.strayanimalrescuesystem.model.LostFoundPet;
import com.example.strayanimalrescuesystem.repository.LostFoundPetRepository;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

import java.util.Base64;
import java.util.List;

@Service
public class AdminService {

    @Autowired
    private LostFoundPetRepository lostFoundPetRepository;

    // Twilio credentials
    private static final String ACCOUNT_SID = "${TWILIO_ACCOUNT_SID}";
    private static final String AUTH_TOKEN = "${TWILIO_AUTH_TOKEN}";
    private static final String TWILIO_PHONE_NUMBER = "${TWILIO_PHONE}";

    static {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    }

    public List<LostFoundPet> getAllLostPets() {
        return lostFoundPetRepository.findByStatus("lost");
    }

    public String convetImageToBase64(byte[] imageBytes) {
        return imageBytes != null ? Base64.getEncoder().encodeToString(imageBytes) : "";
    }

    public void sendMessageToUser(String user_contact_number, String message) {
        Message.creator(
                new
                        com.twilio.type.PhoneNumber(user_contact_number),
                new
                        com.twilio.type.PhoneNumber(TWILIO_PHONE_NUMBER),
                message
        ).create();
        System.out.println("Message sent to: " + user_contact_number + "-" + message);
    }

    public void deletePetRecord(String pet_id) {
        lostFoundPetRepository.deleteById(pet_id);
    }

    // Get all found pet reports
    public List<LostFoundPet> getAllFoundPets() {
        return lostFoundPetRepository.findByStatus("found");
    }
    public void sendfoundUserMessage(String user_contact_number, String message) {
        Message.creator(
                new
                        com.twilio.type.PhoneNumber(user_contact_number),
                new
                        com.twilio.type.PhoneNumber(TWILIO_PHONE_NUMBER),
                message
        ).create();
        System.out.println("Message sent to: " + user_contact_number + "-" + message);
    }
    // Send SMS notification using Twilio
    public void sendNotification(String contactNumber, String message) {
        try {
            Message.creator(
                    new com.twilio.type.PhoneNumber(contactNumber),
                    new com.twilio.type.PhoneNumber(TWILIO_PHONE_NUMBER),
                    message
            ).create();

            System.out.println("Notification sent to: " + contactNumber);
        } catch (Exception e) {
            System.out.println("Failed to send notification: " + e.getMessage());
        }
    }
}
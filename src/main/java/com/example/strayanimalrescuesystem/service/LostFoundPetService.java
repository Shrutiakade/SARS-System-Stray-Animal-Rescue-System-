package com.example.strayanimalrescuesystem.service;

import com.example.strayanimalrescuesystem.model.LostFoundPet;
import com.example.strayanimalrescuesystem.model.User;
import com.example.strayanimalrescuesystem.repository.LostFoundPetRepository;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Base64;
import java.util.List;

@Service
public class LostFoundPetService {

    @Autowired
    private LostFoundPetRepository petRepository;

    private static final String TWILIO_SID = "${TWILIO_ACCOUNT_SID}";
    private static final String TWILIO_AUTH_TOKEN = "${TWILIO_AUTH_TOKEN}";
    private static final String TWILIO_PHONE_NUMBER = "${TWILIO_PHONE}";

    static {
        Twilio.init(TWILIO_SID, TWILIO_AUTH_TOKEN);
    }

    public List<LostFoundPet> getFoundPets() {
        List<LostFoundPet> foundPets = petRepository.findByStatus("found");

        // Convert pet image bytes to Base64
        for (LostFoundPet pet : foundPets) {
            if (pet.getPet_image() != null) {
                String base64Image = Base64.getEncoder().encodeToString(pet.getPet_image());
                pet.setPetImageBase64(base64Image);
            }
        }
        return foundPets;
    }

    public void submitPetReport(LostFoundPet pet, byte[] image, String location, User user) {
        pet.setPet_image(image);
        pet.setLost_location(pet.getStatus().equals("lost") ? location : null);
        pet.setFound_location(pet.getStatus().equals("found") ? location : null);
        petRepository.save(pet);

        // Send notification to admin when a pet is reported
        sendNotificationToAdmin(user.getContactNumber(), pet.getPet_name(), pet.getStatus(), location, pet.getPet_id());
    }

    // Updated method signature
    public void notifyAdmin(String userContact, String petName, String status, String location, Long petId) {
        sendNotificationToAdmin(userContact, petName, status, location, petId);
    }

    // Updated notification message to avoid segment length issue
    private void sendNotificationToAdmin(String userContact, String petName, String status, String location, Long petId) {
        String messageBody = (status.equalsIgnoreCase("found") ?
                "Found pet alert! " :
                "Lost pet alert! ") +
                "Pet: " + petName + ", Location: " + location +
                ", Contact: " + userContact + ", Pet ID: " + petId + ".";

        try {
            Message.creator(
                    new com.twilio.type.PhoneNumber("+918767276665"),
                    new com.twilio.type.PhoneNumber(TWILIO_PHONE_NUMBER),
                    messageBody
            ).create();

            System.out.println("Message sent successfully!");
        } catch (Exception e) {
            System.err.println("Failed to send message: " + e.getMessage());
        }
    }
}
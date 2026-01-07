package com.example.strayanimalrescuesystem.service;

import com.example.strayanimalrescuesystem.model.User;
import com.example.strayanimalrescuesystem.model.WildlifeReport;
import com.example.strayanimalrescuesystem.repository.UserRepository;
import com.example.strayanimalrescuesystem.repository.WildlifeReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.time.LocalDateTime;


@Service
public class WildlifeReportService {

    @Autowired
    private WildlifeReportRepository wildlifeReportRepository;

    @Autowired
    private UserRepository userRepository;

    // Twilio credentials
    private static final String ACCOUNT_SID = "${TWILIO_ACCOUNT_SID}";
    private static final String AUTH_TOKEN = "${TWILIO_AUTH_TOKEN}";
    private static final String TWILIO_PHONE_NUMBER = "${TWILIO_PHONE}";
    private static final String NGO_PHONE_NUMBER = "${NGO_PHONE}"; // NGO's phone number

    static {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    }

    public void submitWildlifeReport(String email, String location, String description, LocalDateTime reportedAt, MultipartFile photo) throws IOException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new RuntimeException("User not found!");
        }

        WildlifeReport report = new WildlifeReport();
        report.setUser(user);
        if (reportedAt == null ) {
            report.setReportedAt(LocalDateTime.now());
        } else {
            report.setReportedAt(reportedAt);
        }

        report.setLocation(location);
        report.setDescription(description);
        report.setStatus("PENDING");

        // Ensure upload folder exists
        String imagePath = "src/main/resources/static/uploads/";
        Path uploadDir = Paths.get(imagePath);
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir); // Create folder if not exists
        }

        // Save image if present
        if (photo != null && !photo.isEmpty()) {
            String fileName = System.currentTimeMillis() + "_" + photo.getOriginalFilename().replace(" ", "");
            Path filePath = uploadDir.resolve(fileName);
            Files.write(filePath, photo.getBytes());

            String imageUrl = "/uploads/" + fileName;
            report.setPhotoUrl(imageUrl);

            // Send SMS notification including image
            sendNotificationToNGO(location, description, imageUrl);
        } else {
            // Send SMS notification without image
            sendNotificationToNGO(location, description, null);
        }

        wildlifeReportRepository.save(report);
    }

    public List<WildlifeReport> getAllReports() {
        return wildlifeReportRepository.findAll();
    }

    private void sendNotificationToNGO(String location, String description, String imageUrl) {
        StringBuilder messageBody = new StringBuilder("🚨 New Wildlife Report 🚨\n");
        messageBody.append("📍 Location: ").append(location).append("\n");
        messageBody.append("📝 Description: ").append(description);

        if (imageUrl != null) {
            messageBody.append("\n📸 Image: http://localhost:9090").append(imageUrl);
        }

        try {
            Message message = Message.creator(
                    new com.twilio.type.PhoneNumber(NGO_PHONE_NUMBER),
                    new com.twilio.type.PhoneNumber(TWILIO_PHONE_NUMBER),
                    messageBody.toString()
            ).create();

            System.out.println("📲 SMS sent successfully! SID: " + message.getSid());
        } catch (Exception e) {
            System.err.println("❌ Failed to send SMS: " + e.getMessage());
        }
    }
}
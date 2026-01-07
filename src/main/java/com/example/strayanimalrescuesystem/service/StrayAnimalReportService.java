package com.example.strayanimalrescuesystem.service;

import com.example.strayanimalrescuesystem.model.StrayAnimalReport;
import com.example.strayanimalrescuesystem.repository.StrayAnimalReportRepository;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.example.strayanimalrescuesystem.model.User;
import java.util.List;
import com.example.strayanimalrescuesystem.repository.UserRepository;
import java.util.Optional;

@Service
public class StrayAnimalReportService {

    @Autowired
    private StrayAnimalReportRepository reportRepository;

    @Autowired
    private UserRepository userRepository; // Add this to fetch user details

    @Value("${TWILIO_ACCOUNT_SID}")
    private String accountSid;

    @Value("${TWILIO_AUTH_TOKEN}")
    private String authToken;

    @Value("${TWILIO_PHONE}")
    private String fromPhoneNumber;

    @Value("${NGO_PHONE}")
    private String ngoContactNumber;

    // Save new report and send SMS
    public StrayAnimalReport submitReport(String location, String description, double latitude, double longitude, byte[] imageData, int id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));

        // Creating report object and ensuring location is set
        StrayAnimalReport report = new StrayAnimalReport(location, description, latitude, longitude, imageData, user);
        report.setLocation(location); // Explicitly setting location

        StrayAnimalReport savedReport = reportRepository.save(report);

        // Prepare SMS message
        String message = "Emergency Alert!\n" +
                "A stray animal has been reported.\n" +
                "Location: " + report.getLatitude() + ", " + report.getLongitude() + ", " + report.getLocation() + "\n" +
                "Description: " + report.getDescription();

        // Send SMS
        sendSMS(ngoContactNumber, message);

        return savedReport;
    }

    public List<StrayAnimalReport> getAllReports() {
        return reportRepository.findAll();
    }

    // Get report history for a user
    public List<StrayAnimalReport> getUserReports(int userId) {
        return reportRepository.findByUserId(userId);
    }

    // Get a single report by ID
    public Optional<StrayAnimalReport> getReportById(Long id) {
        return reportRepository.findById(id);
    }

    // Update report status
    public void updateReportStatus(Long id, String status) {
        Optional<StrayAnimalReport> reportOptional = reportRepository.findById(id);

        if (reportOptional.isPresent()) {
            StrayAnimalReport report = reportOptional.get();
            report.setStatus(StrayAnimalReport.ReportStatus.valueOf(status));
            reportRepository.save(report);

            // Fetch user contact number
            User user = report.getUser();
            String userContactNumber = user.getContactNumber(); // Assuming getContactNumber() exists

            // Send SMS to user
            String msg = "Dear User,\nYour stray animal report has been updated to: " + status +
                    ". Thank you for your concern! - NGO Team";
            sendSMS(userContactNumber, msg);
        }
    }

    public void deleteLocation(Long id) {
        reportRepository.deleteById(id);
    }

    // Send SMS notification
    private void sendSMS(String to, String messageBody) {
        System.out.println("Sending SMS to: " + to);
        System.out.println("Message: " + messageBody);

        try {
            Twilio.init(accountSid, authToken);
            Message msg = Message.creator(
                    new com.twilio.type.PhoneNumber(to),
                    new com.twilio.type.PhoneNumber(fromPhoneNumber),
                    messageBody
            ).create();

            System.out.println("SMS Sent Successfully! SID: " + msg.getSid());
        } catch (Exception e) {
            System.err.println("Error sending SMS: " + e.getMessage());
        }
    }
}

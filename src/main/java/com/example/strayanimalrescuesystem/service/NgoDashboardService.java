package com.example.strayanimalrescuesystem.service;

import com.example.strayanimalrescuesystem.model.WildlifeReport;
import com.example.strayanimalrescuesystem.repository.WildlifeReportRepository;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NgoDashboardService {

    @Autowired
    private WildlifeReportRepository wildlifeReportRepository;

    private static final String ACCOUNT_SID = "${TWILIO_ACCOUNT_SID}";
    private static final String AUTH_TOKEN = "${TWILIO_AUTH_TOKEN}";
    private static final String TWILIO_PHONE_NUMBER = "${TWILIO_PHONE}";

    // Fetch all reports
    public List<WildlifeReport> getAllReports() {
        return wildlifeReportRepository.findAll();
    }

    // Update report status and return the updated report
    public WildlifeReport updateReportStatus(int reportId, String status) {
        WildlifeReport report = wildlifeReportRepository.findById(reportId).orElse(null);
        if (report != null) {
            report.setStatus(status);
            wildlifeReportRepository.save(report);
            return report; // Returning the updated report so we can notify the user
        }
        return null;
    }

    // Notify user via Twilio SMS
    public void notifyUser(WildlifeReport report) {
        if (report.getUser() != null && report.getUser().getContactNumber() != null) {
            Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
            String messageBody = "your status is upated: " + report.getStatus();
            String userPhoneNumber = report.getUser().getContactNumber();

            Message.creator(
                    new com.twilio.type.PhoneNumber(userPhoneNumber),
                    new com.twilio.type.PhoneNumber(TWILIO_PHONE_NUMBER),
                    messageBody
            ).create();
        }
    }
}
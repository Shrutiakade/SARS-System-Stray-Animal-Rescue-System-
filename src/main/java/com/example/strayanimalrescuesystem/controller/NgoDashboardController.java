package com.example.strayanimalrescuesystem.controller;

import com.example.strayanimalrescuesystem.model.WildlifeReport;
import com.example.strayanimalrescuesystem.service.NgoDashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class NgoDashboardController {

    @Autowired
    private NgoDashboardService ngoDashboardService;

    // Fetch all wildlife reports
    @GetMapping("/ngo-dashboard") // Changed this to a cleaner path
    public String getAllWildlifeReports(Model model) {
        List<WildlifeReport> reports = ngoDashboardService.getAllReports();
        if (reports.isEmpty()) {
            model.addAttribute("noReportsMessage", "No wildlife reports found.");
        } else {
            model.addAttribute("reports", reports);
        }
        return "ngo-dashboard"; // Make sure this matches your HTML filename
    }

    // Update report status and notify user
    @PostMapping("/updateReportStatus")
    public String updateReportStatus(@RequestParam("reportId") int reportId,
                                     @RequestParam("status") String status) {
        WildlifeReport updatedReport = ngoDashboardService.updateReportStatus(reportId, status);

        if (updatedReport != null) {
            ngoDashboardService.notifyUser(updatedReport); // Notify user when status is updated
        }

        return "redirect:/ngo-dashboard"; // Updated path
    }
}
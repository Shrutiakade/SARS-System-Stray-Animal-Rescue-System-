package com.example.strayanimalrescuesystem.controller;

import com.example.strayanimalrescuesystem.service.WildlifeReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;

@Controller
public class WildlifeReportController {

    @Autowired
    private WildlifeReportService wildlifeReportService;

    @GetMapping("/wildlife-report")
    public String showWildlifeReportPage() {
        return "wildlife-report";
    }

    @PostMapping("/submitWildlifeReport")
    public String submitReport(
            @RequestParam String email,
            @RequestParam String location,
            @RequestParam String description,
            @RequestParam(required = false) LocalDateTime reportedAt, // Optional
            @RequestParam("photo") MultipartFile photo,
            Model model) {

        try {
            // If reportedAt is empty, use current timestamp
            if (reportedAt == null ) {
                reportedAt = LocalDateTime.now();
            }

            wildlifeReportService.submitWildlifeReport(email, location, description, reportedAt, photo);
            model.addAttribute("message", "Wildlife report submitted successfully!");
            return "dashboard";
        } catch (IOException e) {
            model.addAttribute("error", "Failed to upload the image. Please try again.");
            return "wildlife-report";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "wildlife-report";
        } catch (Exception e) {
            model.addAttribute("error", "An unexpected error occurred. Please try again.");
            return "wildlife-report";
        }
    }
}
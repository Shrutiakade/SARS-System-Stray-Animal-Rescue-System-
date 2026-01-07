package com.example.strayanimalrescuesystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequestMapping("/main-ngo-dashboard")
public class MainNgoDashboardController {

    @GetMapping
    public String showMainNgoDashboard() {
        return "main-ngo-dashboard"; // This should be your HTML file name
    }

    @GetMapping("/wildlife-protection")
    public String showWildlifeProtectionDashboard() {
        return "ngo-dashboard"; // Existing NGO dashboard
    }

    @GetMapping("/stray-animal-rescue")
    public String showStrayRescueReport() {
        return "stray-rescue-report"; // Future stray rescue report page
    }
}
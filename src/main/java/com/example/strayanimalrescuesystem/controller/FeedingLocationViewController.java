package com.example.strayanimalrescuesystem.controller;

import com.example.strayanimalrescuesystem.model.FeedingLocation;
import com.example.strayanimalrescuesystem.service.FeedingLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class FeedingLocationViewController {
    @Autowired
    private FeedingLocationService service;
    @GetMapping("/main-feeding-location")
    public String maindashboardforfeedinglocation(){
        return "main-feeding-location";
    }

    @GetMapping("/add-feeding-location")
    public String showAddFeedingLocationPage() {
        return "add-feeding-Location"; // This will load add-feeding-location.html from templates folder
    }
    @PostMapping("/add-feeding-location")
    public String addLocation(@ModelAttribute FeedingLocation location) {
        service.addLocation(location);
        return "redirect:/main-feeding-location";

    }

    @GetMapping("/feeding-locations-map")
    public String showFeedingLocationsMapPage() {
        return "feeding-locations-map"; // This will load feeding-locations-map.html from templates folder
    }

    @GetMapping("/feeding-locations-list")
    public String showFeedingLocationListPage(Model model){
        List<FeedingLocation> locations=service.getAllLocations();
        model.addAttribute("locations",locations);
        return "feeding-locations-list";
    }
    @GetMapping("/admin-feeding-location")
    public String FeedingLocationListPage(Model model){
        List<FeedingLocation> locations=service.getAllLocations();
        model.addAttribute("locations",locations);
        return "admin-feeding-location";
    }


}


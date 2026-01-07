package com.example.strayanimalrescuesystem.controller;

import com.example.strayanimalrescuesystem.model.FeedingLocation;
import com.example.strayanimalrescuesystem.repository.FeedingLocationRepository;
import com.example.strayanimalrescuesystem.service.FeedingLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

import org.springframework.stereotype.Controller;

@Controller  // Change this from @RestController
@RequestMapping("/feeding-locations")
@CrossOrigin(origins = "*")
public class FeedingLocationController {
    // Your existing code remains the same



    @Autowired
    private FeedingLocationService service;
    @GetMapping
    public ResponseEntity<List<FeedingLocation>> getAllFeedingLocations() {
        List<FeedingLocation> locations = service.getAllLocations();
        return ResponseEntity.ok(locations);
    }

    @GetMapping("/{id}")
    public FeedingLocation getLocationById(@PathVariable Long id) {
        return service.getLocationById(id);
    }

    @PutMapping("/{id}")
    public FeedingLocation updateLocation(@PathVariable Long id, @RequestBody FeedingLocation location) {
        return service.updateLocation(id, location);
    }

    @DeleteMapping("/{id}")
    public void deleteLocation(@PathVariable Long id) {
        service.deleteLocation(id);
    }

    @PatchMapping("/{id}/mark-as-fed")
    public FeedingLocation markAsFed(@PathVariable Long id) {
        return service.markAsFed(id);
    }
    @PostMapping
    public FeedingLocation addLocation(@RequestBody FeedingLocation location){
        return service.addLocation(location);
    }
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        FeedingLocation location = service.getLocationById(id);
        if (location == null) {
            return "redirect:/feeding-locations";  // Redirect if location not found
        }
        model.addAttribute("location", location);
        return "edit-feeding-location";
    }

    @PostMapping("/update")
    public String updateFeedingLocation(@RequestParam("id") Long id, @ModelAttribute FeedingLocation location) {
        service.updateLocation(id, location);
        return "redirect:/admin-feeding-location";
    }
    @GetMapping("/add")
    public String showAddLocationForm(Model model) {
        model.addAttribute("location", new FeedingLocation());
        return "add-feeding-location";  // Create this Thymeleaf HTML form
    }
    @PostMapping("/add")
    public String addFeedingLocation(@ModelAttribute FeedingLocation location, RedirectAttributes redirectAttributes) {
       service.addLocation(location);
        redirectAttributes.addFlashAttribute("successMessage", "Feeding location added successfully!");
        return "redirect:/admin-feeding-location";
    }

    @GetMapping("/delete/{id}")
    public String deleteFeedingLocation(@PathVariable("id") Long id) {
        service.deleteLocation(id);
        return "redirect:/admin-feeding-location";
    }


}
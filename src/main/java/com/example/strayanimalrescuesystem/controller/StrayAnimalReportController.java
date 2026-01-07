package com.example.strayanimalrescuesystem.controller;

import com.example.strayanimalrescuesystem.model.StrayAnimalReport;
import com.example.strayanimalrescuesystem.model.User;
import com.example.strayanimalrescuesystem.repository.UserRepository;
import com.example.strayanimalrescuesystem.service.StrayAnimalReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import java.util.HashMap;
import java.util.Map;
import java.util.Base64;

@RestController
@RequestMapping("/api/stray-reports")
public class StrayAnimalReportController {

    @Autowired
    private StrayAnimalReportService reportService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/all")
    public ResponseEntity<List<Map<String, Object>>> getAllAnimalReports() {
        List<StrayAnimalReport> reports = reportService.getAllReports();
        List<Map<String, Object>> reportList = reports.stream().map(report -> {
            Map<String, Object> reportMap = new HashMap<>();
            reportMap.put("id", report.getId());
            reportMap.put("description", report.getDescription());
            reportMap.put("latitude", report.getLatitude());
            reportMap.put("longitude", report.getLongitude());
            reportMap.put("location", report.getLocation());
            reportMap.put("status", report.getStatus());

            if (report.getImage() != null) {
                String base64Image = Base64.getEncoder().encodeToString(report.getImage());
                reportMap.put("base64Image", base64Image);
            } else {
                reportMap.put("base64Image", null);
            }
            return reportMap;
        }).toList();

        return ResponseEntity.ok(reportList);
    }

    @GetMapping
    public ResponseEntity<List<StrayAnimalReport>> getAllReports() {
        List<StrayAnimalReport> reports = reportService.getAllReports();
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, "application/json").body(reports);
    }

    @PostMapping("/submit")
    public ResponseEntity<Map<String, String>> submitReport(
            @RequestParam("image") MultipartFile image,
            @RequestParam("description") String description,
            @RequestParam("latitude") double latitude,
            @RequestParam("longitude") double longitude,
            @RequestParam("location") String location,
            HttpSession session) {
        System.out.println("Received location: " + location);

        Map<String, String> response = new HashMap<>();
        Object userIdObj = session.getAttribute("id");
        if (userIdObj == null) {
            response.put("message", "User not logged in!");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        int userId = Integer.parseInt(userIdObj.toString());
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isPresent()) {
            try {
                byte[] imageData = image.getBytes();
                String base64Image = Base64.getEncoder().encodeToString(imageData);

                User user = userOptional.get();
                reportService.submitReport(location, description, latitude, longitude, imageData, user.getId());

                response.put("message", "Report submitted successfully!");
                response.put("image", "data:image/jpeg;base64," + base64Image);
                return ResponseEntity.ok(response);
            } catch (IOException e) {
                response.put("message", "Error processing image file!");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }
        } else {
            response.put("message", "User not found!");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping("/user/{userId}")
    public List<StrayAnimalReport> getUserReports(@PathVariable int userId) {
        return reportService.getUserReports(userId);
    }

    @GetMapping("/{id}")
    public Optional<StrayAnimalReport> getReportById(@PathVariable Long id) {
        return reportService.getReportById(id);
    }

    @PutMapping("/update-status/{id}")
    public String updateStatus(@PathVariable Long id, @RequestParam String status) {
        reportService.updateReportStatus(id, status);
        return "Status updated to " + status;
    }

    @GetMapping("/delete/{id}")
    public String deleteFeedingLocation(@PathVariable("id") Long id) {
        reportService.deleteLocation(id);
        return "redirect:/ngo-stray-reports";
    }
}

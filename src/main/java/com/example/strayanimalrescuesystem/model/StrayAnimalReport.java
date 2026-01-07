package com.example.strayanimalrescuesystem.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "stray_animal_reports")
public class StrayAnimalReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[]image;
    private String location;
    private Double latitude;
    private Double longitude;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReportStatus status = ReportStatus.PENDING;

    private LocalDateTime reportedAt = LocalDateTime.now();

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public byte[] getImage() { return image; }
    public void setImage(byte[] image) { this.image = image; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }

    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public ReportStatus getStatus() { return status; }
    public void setStatus(ReportStatus status) { this.status = status; }

    public LocalDateTime getReportedAt() { return reportedAt; }
    public void setReportedAt(LocalDateTime reportedAt) { this.reportedAt = reportedAt; }

    // *Nested Enum for Status*
    public enum ReportStatus {
        PENDING,
        IN_PROGRESS,
        RESOLVED
    }
    public StrayAnimalReport(){

    }
    public StrayAnimalReport(String location,String description, double latitude, double longitude, byte[] image,User user) {
        this.location=location;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.image = image;
        this.user=user;
    }
    private String base64Image;
    public String getBase64Image(){
        return base64Image;
    }
    public void setBase64Image(String base64Image){
        this.base64Image=base64Image;
    }
}
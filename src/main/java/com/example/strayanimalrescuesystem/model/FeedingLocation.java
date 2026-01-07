package com.example.strayanimalrescuesystem.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
public class FeedingLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long locationId;
    private double longitude;
    private double latitude;
    private String address;
    private String description;
    private LocalDateTime lastFed;
    private LocalDateTime nextFeedingSchedule;

    public FeedingLocation(){

    }

    public FeedingLocation(double longitude,double latitude,String address,String description, LocalDateTime lastFed,LocalDateTime nextFeedingSchedule){
        this.longitude=longitude;
        this.latitude=latitude;
        this.address=address;
        this.description=description;
        this.lastFed=lastFed;
        this.nextFeedingSchedule=nextFeedingSchedule;
    }

    public LocalDateTime getNextFeedingSchedule() {
        return nextFeedingSchedule;
    }

    public void setNextFeedingSchedule(LocalDateTime nextFeedingSchedule) {
        this.nextFeedingSchedule = nextFeedingSchedule;
    }

    public Long getLocationId() {
        return locationId;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public LocalDateTime getLastFed() {
        return lastFed;
    }

    public void setLastFed(LocalDateTime lastFed) {
        this.lastFed = lastFed;
    }

    public String getAddress() {
        return address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
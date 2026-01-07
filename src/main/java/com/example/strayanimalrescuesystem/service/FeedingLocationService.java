package com.example.strayanimalrescuesystem.service;

import com.example.strayanimalrescuesystem.model.FeedingLocation;
import com.example.strayanimalrescuesystem.repository.FeedingLocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedingLocationService {

    @Autowired
    private FeedingLocationRepository repository;

    public FeedingLocation addLocation(FeedingLocation location) {
        return repository.save(location);
    }

    public List<FeedingLocation> getAllLocations() {
        return repository.findAll();
    }

    public FeedingLocation getLocationById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public FeedingLocation updateLocation(Long id, FeedingLocation updatedLocation) {
        FeedingLocation location = repository.findById(id).orElseThrow(()->new RuntimeException("Feeding Location not found!"));

            location.setLatitude(updatedLocation.getLatitude());
            location.setLongitude(updatedLocation.getLongitude());
            location.setAddress(updatedLocation.getAddress());
            location.setDescription(updatedLocation.getDescription());
            location.setNextFeedingSchedule(updatedLocation.getNextFeedingSchedule());
            return repository.save(location);
        }



    public void deleteLocation(Long id) {
        repository.deleteById(id);
    }

    public FeedingLocation markAsFed(Long id) {
        FeedingLocation location = repository.findById(id).orElse(null);
        if (location != null) {
            location.setLastFed(java.time.LocalDateTime.now());
            return repository.save(location);
        }
        return null;
    }
}
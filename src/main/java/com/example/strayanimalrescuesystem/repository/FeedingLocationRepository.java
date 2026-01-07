package com.example.strayanimalrescuesystem.repository;

import com.example.strayanimalrescuesystem.model.FeedingLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedingLocationRepository extends JpaRepository<FeedingLocation, Long> {
}
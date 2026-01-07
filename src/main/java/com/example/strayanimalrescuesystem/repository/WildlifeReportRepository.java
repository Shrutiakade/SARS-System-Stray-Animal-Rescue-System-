package com.example.strayanimalrescuesystem.repository;

import com.example.strayanimalrescuesystem.model.WildlifeReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WildlifeReportRepository extends JpaRepository<WildlifeReport, Integer> {
}
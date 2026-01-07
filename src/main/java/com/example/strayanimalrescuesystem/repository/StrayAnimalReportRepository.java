package com.example.strayanimalrescuesystem.repository;
import com.example.strayanimalrescuesystem.model.StrayAnimalReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface StrayAnimalReportRepository extends JpaRepository<StrayAnimalReport, Long> {
    List<StrayAnimalReport> findByUserId(int userId);

}
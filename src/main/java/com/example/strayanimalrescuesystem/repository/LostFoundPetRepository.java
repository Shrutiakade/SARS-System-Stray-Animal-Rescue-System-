package com.example.strayanimalrescuesystem.repository;

import com.example.strayanimalrescuesystem.model.LostFoundPet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LostFoundPetRepository extends JpaRepository<LostFoundPet, String> {
 List<LostFoundPet> findByStatus(String status);
}
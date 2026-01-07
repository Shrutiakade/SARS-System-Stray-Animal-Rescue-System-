package com.example.strayanimalrescuesystem.repository;

import com.example.strayanimalrescuesystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);
    User findByContactNumber(String contactNumber);

}
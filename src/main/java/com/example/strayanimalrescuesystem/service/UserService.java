package com.example.strayanimalrescuesystem.service;

import com.example.strayanimalrescuesystem.model.User;
import com.example.strayanimalrescuesystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public String registerUser(String username, String email, String contactNumber,String UserType,String password) {
        if (userRepository.findByEmail(email) != null) {
            return "Email already registered!";
        }

        User user = new User(0, username, email, contactNumber,UserType,password);
        userRepository.save(user);
        return "Registration successful!";
    }

    public User loginUser(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
}
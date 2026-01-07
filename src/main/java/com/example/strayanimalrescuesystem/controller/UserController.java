package com.example.strayanimalrescuesystem.controller;

import com.example.strayanimalrescuesystem.model.User;
import com.example.strayanimalrescuesystem.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/register")
    public String showRegistrationForm() {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String username,
                               @RequestParam String email,
                               @RequestParam String contactNumber,
                               @RequestParam String userType,
                               @RequestParam String password,
                               Model model) {

        // Check if user already exists by email or contact number
        if (userRepository.findByEmail(email) != null || userRepository.findByContactNumber(contactNumber) != null) {
            model.addAttribute("error", "User already exists! Please log in.");
            return "register";
        }

        // Save new user
        User user = new User(0, username, email, contactNumber, userType, password);
        userRepository.save(user);
        model.addAttribute("message", "Registered successfully! Please log in.");
        return "login"; // Redirecting to login page after registration
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }


    @PostMapping("/login")
    public String loginUser(@RequestParam String email,
                            @RequestParam String password,
                            Model model, HttpSession session) {
        User user = userRepository.findByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            session.setAttribute("loggedInUser",user);
            session.setAttribute("email", user.getEmail());
            session.setAttribute("id", user.getId());
            session.setAttribute("userType", user.getUserType());
            model.addAttribute("userType", user.getUserType());
            model.addAttribute("message", "Login successful!");
            System.out.println("UserType set as: " + session.getAttribute("userType"));

            // If user is NGO, redirect to Main NGO Dashboard
            if ("NGO".equalsIgnoreCase(user.getUserType())) {
                return "redirect:/main-ngo-dashboard"; // New NGO dashboard
            } else if ("ADMIN".equalsIgnoreCase(user.getUserType())) {
                return "redirect:/admin-dashboard";
            } else {
                return "dashboard"; // Existing user dashboard
            }

        }
        else {
            model.addAttribute("error", "Invalid email or password!");
            return "login";
        }
    }
}
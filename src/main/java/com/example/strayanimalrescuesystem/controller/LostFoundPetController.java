package com.example.strayanimalrescuesystem.controller;

import com.example.strayanimalrescuesystem.model.LostFoundPet;
import com.example.strayanimalrescuesystem.model.User;
import com.example.strayanimalrescuesystem.service.LostFoundPetService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.List;

@Controller
public class LostFoundPetController {

    @Autowired
    private LostFoundPetService petService;


    @GetMapping("/lost-found-pet-form")
    public String showlostfoundpetpage() {
        return "lost-found-pet";
    }

    @PostMapping("/lost-found-pet/submit")
    public String submitPetReport(@RequestParam("pet_name") String petName,
                                  @RequestParam("pet_type") String petType,
                                  @RequestParam("pet_breed") String petBreed,
                                  @RequestParam("status") String status,
                                  @RequestParam("location") String location,
                                  @RequestParam("user_contact_number") String userContact,
                                  @RequestParam("pet_image") MultipartFile petImage, HttpSession session) {
        try {
            User loggedInUser = (User) session.getAttribute("loggedInUser");
            if (loggedInUser == null) {
                return "redirect:/login";
            }

            LostFoundPet petInfo = new LostFoundPet();
            petInfo.setPet_name(petName);
            petInfo.setPet_type(petType);
            petInfo.setPet_breed(petBreed);
            petInfo.setStatus(status);
            petInfo.setUser_contact_number(userContact);
            petInfo.setUser(loggedInUser);

            petService.submitPetReport(petInfo, petImage.getBytes(), location, loggedInUser);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "redirect:/lost-found-pet-form";
    }

    @GetMapping("/lost-found")
    public String showlostfoundpage() {
        return "lost-found";
    }


    @GetMapping("/found-pet")
    public String viewFoundPets(Model model) {
        List<LostFoundPet> foundPets = petService.getFoundPets();
        model.addAttribute("foundPets", foundPets);
        return "view-found-pet";
    }

    @PostMapping("/notify-admin")
    public String notifyAdmin(@RequestParam("pet_name") String petName,
                              @RequestParam("found_location") String location,
                              @RequestParam("user_contact") String userContact,
                              @RequestParam("pet_id") Long petId,
                              @RequestParam("status") String status) {
        System.out.println("DEBUG: notifyadmin() called for petId "+petId);
        petService.notifyAdmin(userContact, petName, status, location, petId);

        return "redirect:/found-pet";
    }
}
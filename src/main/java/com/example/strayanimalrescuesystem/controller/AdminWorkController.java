package com.example.strayanimalrescuesystem.controller;




import com.example.strayanimalrescuesystem.model.FeedingLocation;
import com.example.strayanimalrescuesystem.model.LostFoundPet;
import com.example.strayanimalrescuesystem.service.AdminService;
import com.example.strayanimalrescuesystem.service.FeedingLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@Controller
public class AdminWorkController {

    @Autowired
    private AdminService adminService;
    @Autowired
    private FeedingLocationService service;


    @GetMapping("/admin/lost-pet-claims")
    public String viewLostPets(Model model) {
        List<LostFoundPet> lostPets = adminService.getAllLostPets();
        lostPets.forEach(pet ->pet.setPetImageBase64(adminService.convetImageToBase64(pet.getPet_image())));
        model.addAttribute("lostPets", lostPets);
        return "lost-pet-claims";
    }
    @PostMapping("/admin/send-message")
    public String sendUserMessage(@RequestParam("user_contact_number")String user_contact_number){
        adminService.sendMessageToUser(user_contact_number,"Congratulations,finally you got your pet");
        return "redirect:/admin/lost-pet-claims";
    }
    @PostMapping("/admin/delete-record")
    public String deletePetRecord(@RequestParam("pet_id") String pet_id){
        adminService.deletePetRecord(pet_id);
        return "redirect:/admin/lost-pet-claims";
    }
    @GetMapping("/admin/found-pet-reports")
    public String viewFoundPets(Model model) {
        List<LostFoundPet> lostPets = adminService.getAllFoundPets();
        lostPets.forEach(pet ->pet.setPetImageBase64(adminService.convetImageToBase64(pet.getPet_image())));
        model.addAttribute("foundPets", lostPets);
        return "found-pet-reports";
    }
    @PostMapping("/admin/send-message-found")
    public String sendfoundUserMessage(@RequestParam("user_contact_number")String user_contact_number){
        adminService.sendMessageToUser(user_contact_number,"Pet owner found kindly come to office ");
        return "redirect:/admin/found-pet-reports";
    }
    @PostMapping("/admin/delete-found-record")
    public String deleteFoundPetRecord(@RequestParam("pet_id") String pet_id){
        adminService.deletePetRecord(pet_id);
        return "redirect:/admin/found-pet-reports";
    }



    @GetMapping("/send-notifications")
    public String showNotificationForm() {
        return "send-notifications";
    }



    @PostMapping("/send-notifications")
    public String sendNotification(@RequestParam String contactNumber, @RequestParam String message) {
        adminService.sendNotification(contactNumber, message);
        return "redirect:/admin-dashboard";
    }



}
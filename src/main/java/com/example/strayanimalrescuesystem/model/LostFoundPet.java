package com.example.strayanimalrescuesystem.model;

import jakarta.persistence.*;

@Entity
@Table(name = "lost_found_pet_info")
public class LostFoundPet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pet_id;

    @Lob
    @Column(nullable = false,name = "pet_image",columnDefinition = "LONGBLOB")
    private byte[] pet_image;

    @Column(nullable = false)
    private String pet_name;

    private String petImageBase64;



    @Column(nullable = false)
    private String pet_type;

    @Column(nullable = false)
    private String pet_breed;

    @Column(nullable = false)
    private String status; // 'lost' or 'found'

    @Column(nullable = false)
    private String user_contact_number;

    @Column(nullable = true)
    private String lost_location;
    @Column(nullable = true)
    private String found_location;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Getters and Setters
    public Long getPet_id() {
        return pet_id;
    }


    public void setPet_id(Long pet_id) {
        this.pet_id = pet_id;
    }

    public byte[] getPet_image() {
        return pet_image;
    }

    public void setPet_image(byte[] pet_image) {
        this.pet_image = pet_image;
    }

    public String getPet_name() {
        return pet_name;
    }

    public void setPet_name(String pet_name) {
        this.pet_name = pet_name;
    }

    public String getPet_type() {
        return pet_type;
    }

    public void setPet_type(String pet_type) {
        this.pet_type = pet_type;
    }

    public String getPet_breed() {
        return pet_breed;
    }

    public void setPet_breed(String pet_breed) {
        this.pet_breed = pet_breed;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUser_contact_number() {
        return user_contact_number;
    }

    public void setUser_contact_number(String user_contact_number) {
        this.user_contact_number = user_contact_number;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }



    public String getLost_location() {
        return lost_location;
    }

    public void setLost_location(String lost_location) {
        this.lost_location = lost_location;
    }

    public String getFound_location() {
        return found_location;
    }

    public void setFound_location(String found_location) {
        this.found_location = found_location;
    }
    // Getters and Setters
    public String getPetImageBase64() {
        return petImageBase64;
    }

    public void setPetImageBase64(String petImageBase64) {
        this.petImageBase64 = petImageBase64;
    }
}
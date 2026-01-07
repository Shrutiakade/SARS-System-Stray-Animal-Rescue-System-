package com.example.strayanimalrescuesystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StrayAnimalViewController {

    @GetMapping("/main-stray-animal")
    public String mainpage(){
        return "main-stray-animal";
    }
    @GetMapping("/stray-animal-report")
    public String showStrayAnimalReportForm(){
        return "stray-animal-report";
    }
    @GetMapping("/ngo-stray-animal-dashboard")
    public String showngostraydash(){
        return "ngo-stray-dash";
    }
    @GetMapping("/see-all-locations")
    public String showAlllocations(){
        return "see-all-locations";
    }
    @GetMapping("/hotspot-location")
    public String showAllhotspotlocations(){
        return "Hotspot-location";
    }
    @GetMapping("/ngo-stray-report")
    public String showAllngoreports(){
        return "ngo-stray-reports";
    }
}

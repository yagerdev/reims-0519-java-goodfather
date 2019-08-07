package fr.wildcodeschool.goodFather.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

public class ProjectController {

    @GetMapping("/profil")
    public String profil() {
        return "profil";
    }
    
    @PostMapping
    public String project() {
        return "redirect:/projet";
    }

}
package fr.wildcodeschool.goodFather.controllers;

import org.springframework.web.bind.annotation.GetMapping;

public class ProjectController {

    @GetMapping("/profil")
    public String profil() {
        return "profil";
    }
    
    
}
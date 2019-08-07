package fr.wildcodeschool.goodFather.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

public class ProjectController {

    @GetMapping("/profil")
    public String show() {
        return "profil";
    }
    
    @PostMapping("/profil")
    public String create() {
        return "redirect:/profil";
    }

}
package fr.wildcodeschool.goodFather.controllers;

import fr.wildcodeschool.goodFather.entities.Project;
import fr.wildcodeschool.goodFather.repositories.ProjectRepository;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

public class ProjectController {

    @GetMapping("/profil")
    public String showProfil() {
        return "profil";
    }
    
    @PostMapping("/profil")
    public String createProject(@RequestParam String name, @RequestParam String address, @RequestParam String city, @RequestParam String postalCode) {
        Project project = new Project(name, address, city, postalCode);
        ProjectRepository.save(project);
        return "redirect:/project";
    }

    @GetMapping("/project")
    public String showProject() {
        return "project";
    }
    
}
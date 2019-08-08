package fr.wildcodeschool.goodFather.controllers;

import fr.wildcodeschool.goodFather.entities.Project;
import fr.wildcodeschool.goodFather.repositories.ProjectRepository;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;


public class ProjectController {
    
    @PostMapping("/projects/create")
    public String createProject(@RequestParam String name, @RequestParam String address, @RequestParam String city, @RequestParam String postalCode) {
        Project project = new Project(name, address, city, postalCode);
        ProjectRepository.save(project);
        return "redirect:/project/id/edit";
    }

    @GetMapping("/projects/create")
    public String showProject() {
        return "project-create";
    }

    @GetMapping("project/{id}/edit")
    public String showCatetories(@PathVariable("id") Long projectId, @RequestParam Long categoryId){
        if(categoryId == null) {
            return "categories";
        }
        else {
            return "redirect:/rooms/create?projectId={categoryId}";
        }
    }
    
    
}
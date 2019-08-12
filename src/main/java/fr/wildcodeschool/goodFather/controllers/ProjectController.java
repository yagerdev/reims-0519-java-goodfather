package fr.wildcodeschool.goodFather.controllers;

import fr.wildcodeschool.goodFather.entities.Category;
import fr.wildcodeschool.goodFather.entities.Project;
import fr.wildcodeschool.goodFather.repositories.CategoryRepository;
import fr.wildcodeschool.goodFather.repositories.ProjectRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProjectController {
    
    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    CategoryRepository categoryRepository;
    
    @PostMapping("/projects")
    public String createProject(
        @RequestParam String name,
        @RequestParam String address,
        @RequestParam String city,
        @RequestParam String postalCode
    ) {
        Project project = new Project(name, address, city, postalCode);
        project = projectRepository.save(project);
        Long id = project.getId();
        return "redirect:/projects/"+id+"/edit";
    }

    @GetMapping("/projects/create")
    public String showCreateProjectForm() {
        return "project-create";
    }

    @GetMapping("projects/{id}/edit")
    public String showCategories(
            Model model,
            @PathVariable("id") Long projectId,
            @RequestParam(required=false) Long categoryId
        ) {
        List<Category> categoryList = categoryRepository.findAll();
        model.addAttribute("categories", categoryList);
        if(categoryId == null) {
            return "categories";
        }
        else {
            return "redirect:/rooms/create?projectId="+projectId+"&categoryId="+categoryId;
        }
    }
}
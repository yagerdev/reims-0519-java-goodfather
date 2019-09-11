package fr.wildcodeschool.goodFather.controllers;

import fr.wildcodeschool.goodFather.entities.Category;
import fr.wildcodeschool.goodFather.entities.Project;
import fr.wildcodeschool.goodFather.entities.User;
import fr.wildcodeschool.goodFather.repositories.CategoryRepository;
import fr.wildcodeschool.goodFather.repositories.ProjectRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public String create(
        @RequestParam String name,
        @RequestParam String address,
        @RequestParam String city,
        @RequestParam String postalCode
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User)authentication.getPrincipal();
        Project project = new Project(name, address, city, postalCode,currentUser);
        project = projectRepository.save(project);
        Long id = project.getId();
        return "redirect:/projects/"+id+"/edit";
    }

    @GetMapping("/projects/create")
    public String showCreateProjectForm() {
        return "project-create";
    }
    @GetMapping("/projects")
    public String showAllProjectsByUser(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<Project> projectsList = projectRepository.findAllByUser(authentication.getPrincipal());
        model.addAttribute("projects", projectsList);
        return "projects";
    }

    @GetMapping("/projects/{projectId}/edit")
    public String show(
            Model model,
            @PathVariable("projectId") Long projectId,
            @RequestParam(required=false) Long categoryId
        ) {
        if(categoryId == null) {
            List<Category> categoryList = categoryRepository.findAll();
            model.addAttribute("categories", categoryList);
            model.addAttribute("projectId", projectId);
            return "categories";
        }
        else {
            return "redirect:/rooms/create?projectId="+projectId+"&categoryId="+categoryId;
        }
    }
}
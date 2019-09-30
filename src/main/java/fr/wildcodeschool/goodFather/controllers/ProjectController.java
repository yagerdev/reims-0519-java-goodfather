package fr.wildcodeschool.goodFather.controllers;

import fr.wildcodeschool.goodFather.entities.Category;
import fr.wildcodeschool.goodFather.entities.Project;
import fr.wildcodeschool.goodFather.entities.Room;
import fr.wildcodeschool.goodFather.entities.User;
import fr.wildcodeschool.goodFather.repositories.CategoryRepository;
import fr.wildcodeschool.goodFather.repositories.ProjectRepository;
import fr.wildcodeschool.goodFather.repositories.UserRepository;
import fr.wildcodeschool.goodFather.repositories.RoomRepository;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.xml.PluggableSchemaResolver;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ProjectController {

    @Autowired
    UserRepository userRepository;
    
    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    RoomRepository roomRepository;
    
    @PostMapping("/projects")
    public String create(@ModelAttribute Project project, Authentication authentication) {
        User currentUser = (User) authentication.getPrincipal();
        project.setUser(currentUser);
        project.setCreationDate(new Date());
        project = projectRepository.save(project);
        return "redirect:/projects/" + project.getId() + "/edit";
    }

    @GetMapping("/projects/create")
    public String showCreateProjectForm(Model model, Authentication authentication) {
        User currentUser = (User)authentication.getPrincipal();
        model.addAttribute("user", currentUser);
        return "project-create";
    }

    @GetMapping("projects/{id}")
    public String read(
        @PathVariable Long id,
        Model model,
        @RequestParam(value = "message", required = false) String message
    ) {
        Project project = projectRepository.findById(id).get();
        List<Category> categoryList = categoryRepository.findAll();
        Set<Room> rooms = project.getRooms();
        model.addAttribute("project", project);
        model.addAttribute("rooms", rooms);
        model.addAttribute("message", message);
        model.addAttribute("categories", categoryList);
        return "project-recap";
    }

    @GetMapping("/projects")
    public String showAllProjectsByUser(
        Model model,
        Authentication authentication,
        @RequestParam(value = "message", required = false) String message
    ) {
        List<Project> projectsList = projectRepository.findAllByUser(authentication.getPrincipal());
        model.addAttribute("projects", projectsList);
        model.addAttribute("message", message);
        return "projects";
    }

    @GetMapping("/projects/{projectId}/edit")
    public String show(
        Model model, 
        @PathVariable("projectId") Long projectId,
        @RequestParam(required = false) Long categoryId
    ) {
        if (categoryId == null) {
            List<Category> categoryList = categoryRepository.findAll();
            model.addAttribute("categories", categoryList);
            model.addAttribute("projectId", projectId);
            return "categories";
        } else {
            return "redirect:/rooms/create?projectId=" + projectId + "&categoryId=" + categoryId;
        }
    }

    @DeleteMapping("/projects/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        projectRepository.deleteById(id);
        redirectAttributes.addAttribute("message", "delete");
        return "redirect:/projects";
    }

    @GetMapping("projects/{id}/modify")
    public String modify(@PathVariable Long id, Long projectId, Model model){

        Project project = projectRepository.findById(id).get();
        System.out.println(project);
        model.addAttribute("project", project);
        return "project-edit";
    }

    @PutMapping("projects/{id}/modify")
    public String update(RedirectAttributes redirectAttributes,
    @PathVariable Long id,
    @RequestParam String name, 
    @RequestParam String address, 
    @RequestParam String city, 
    @RequestParam String postalCode, 
    @RequestParam String comment, 
    Model model
    ){
        System.out.println(id);
        Project projectToUpdate = projectRepository.findById(id).get();
        projectToUpdate.setName(name);
        projectToUpdate.setAddress(address);
        projectToUpdate.setCity(city);
        projectToUpdate.setComment(comment);
        projectToUpdate.setPostalCode(postalCode);
        projectToUpdate = projectRepository.save(projectToUpdate);
        model.addAttribute("project", projectToUpdate);
        return"project-edit";
    }
}
// to do faire des request param spécifique
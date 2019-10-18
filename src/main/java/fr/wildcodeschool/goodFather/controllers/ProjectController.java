package fr.wildcodeschool.goodFather.controllers;

import fr.wildcodeschool.goodFather.entities.Category;
import fr.wildcodeschool.goodFather.entities.Project;
import fr.wildcodeschool.goodFather.entities.Quantity;
import fr.wildcodeschool.goodFather.entities.Room;
import fr.wildcodeschool.goodFather.entities.Task;
import fr.wildcodeschool.goodFather.entities.Typology;
import fr.wildcodeschool.goodFather.entities.User;
import fr.wildcodeschool.goodFather.repositories.CategoryRepository;
import fr.wildcodeschool.goodFather.repositories.ProjectRepository;
import fr.wildcodeschool.goodFather.repositories.QuantityRepository;
import fr.wildcodeschool.goodFather.repositories.UserRepository;
import fr.wildcodeschool.goodFather.repositories.RoomRepository;
import fr.wildcodeschool.goodFather.repositories.TaskRepository;
import fr.wildcodeschool.goodFather.repositories.TypologyRepository;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    QuantityRepository quantityRepository;

    @Autowired
    TypologyRepository typologyRepository;

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
        User currentUser = (User) authentication.getPrincipal();
        model.addAttribute("user", currentUser);
        return "project-create";
    }

    @GetMapping("/projects/{id}/resume")
    public String readAllProject(Model model, @PathVariable Long id, Authentication authentication) {
        Project project = projectRepository.findById(id).get();
        User currentUser = (User) authentication.getPrincipal();
        Long userId = currentUser.getId();
        Long projectUserId = project.getUser().getId();
        if (userId.equals(projectUserId)) {
            project = projectRepository.findById(id).get();
            List<Category> categoryList = categoryRepository.findAll();
            Collections.sort(categoryList);
            List<Typology> typologies = typologyRepository.findAll();
            Collections.sort(typologies);
            model.addAttribute("typologies", typologies);
            TreeSet<Room> rooms = new TreeSet<Room>(project.getRooms());
            model.addAttribute("project", project);
            model.addAttribute("rooms", rooms);
            model.addAttribute("categories", categoryList);
            model.addAttribute("user", currentUser);
            return "resume";
        }
        return "error";
    }

    @GetMapping("projects/{id}")
    public String read(
        @PathVariable Long id,
        Model model,
        @RequestParam(required = false) String message,
        Authentication authentication
    ) {
        Project projectToUpdate = projectRepository.findById(id).get();
        User currentUser = (User) authentication.getPrincipal();
        Long userId = currentUser.getId();
        Long projectUserId = projectToUpdate.getUser().getId();
        if (userId.equals(projectUserId)) {
            Project project = projectRepository.findById(id).get();
            List<Category> categoryList = categoryRepository.findAll();
            Collections.sort(categoryList);
            TreeSet<Room> rooms = new TreeSet<Room>(project.getRooms());
            model.addAttribute("project", project);
            model.addAttribute("rooms", rooms);
            model.addAttribute("message", message);
            model.addAttribute("categories", categoryList);
            return "project-recap";
        }
        return "error";
    }

    @GetMapping("/projects")
    public String showAllProjectsByUser(Model model, Authentication authentication,
            @RequestParam(required = false) String message) {
        List<Project> projectsList = projectRepository.findAllByUser(authentication.getPrincipal());
        Collections.sort(projectsList);
        model.addAttribute("projects", projectsList);
        model.addAttribute("message", message);
        return "projects";
    }

    @GetMapping("/projects/{projectId}/edit")
    public String show(
        Model model,
        @PathVariable("projectId") Long projectId,
        @RequestParam(required = false) Long categoryId,
        Authentication authentication
    ) {
        Project projectToUpdate = projectRepository.findById(projectId).get();
        User currentUser = (User) authentication.getPrincipal();
        Long userId = currentUser.getId();
        Long projectUserId = projectToUpdate.getUser().getId();
        if (userId.equals(projectUserId)) {
            if (categoryId == null) {
                List<Category> categoryList = categoryRepository.findAll();
                Collections.sort(categoryList);
                model.addAttribute("categories", categoryList);
                model.addAttribute("projectId", projectId);
                return "categories";
            } else {
                return "redirect:/rooms/create?projectId=" + projectId + "&categoryId=" + categoryId;
            }
        }
        return "error";
    }

    @DeleteMapping("/projects/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        projectRepository.deleteById(id);
        redirectAttributes.addAttribute("message", "delete");
        return "redirect:/projects";
    }

    @GetMapping("projects/{id}/update")
    public String update(@PathVariable Long id, Long projectId, Model model, Authentication authentication) {
        Project projectToUpdate = projectRepository.findById(id).get();
        User currentUser = (User) authentication.getPrincipal();
        Long userId = currentUser.getId();
        Long projectUserId = projectToUpdate.getUser().getId();
        Project project = projectRepository.findById(id).get();
        if (userId.equals(projectUserId)) {
            model.addAttribute("project", project);
            return "project-edit";
        }
        return "error";
    }

    @PutMapping("projects/{id}/update")
    public String update(
        RedirectAttributes redirectAttributes,
        @PathVariable Long id,
        Project project,
        Model model,
        Authentication authentication
    ) {
        Project projectToUpdate = projectRepository.findById(id).get();
        User currentUser = (User) authentication.getPrincipal();
        Long userId = currentUser.getId();
        Long projectUserId = projectToUpdate.getUser().getId();
        if (userId.equals(projectUserId)) {
            projectToUpdate.setName(project.getName());
            projectToUpdate.setAddress(project.getAddress());
            projectToUpdate.setCity(project.getCity());
            projectToUpdate.setComment(project.getComment());
            projectToUpdate.setPostalCode(project.getPostalCode());
            changePriceSource(projectToUpdate, projectToUpdate.getSourceId(), project.getSourceId());
            projectToUpdate.setCreationDate(project.getCreationDate());
            projectToUpdate = projectRepository.save(projectToUpdate);
            model.addAttribute("project", projectToUpdate);
            return "redirect:/projects/" + id;
        } else {
            return "error";
        }
    }

    @PutMapping("projects/{id}")
    public String updateComment(@PathVariable Long id, Project project, RedirectAttributes redirectAttributes) {
        Project projectToUpdate = projectRepository.findById(id).get();
        projectToUpdate.setComment(project.getComment());
        projectRepository.save(projectToUpdate);
        redirectAttributes.addAttribute("message", "edit");
        return "redirect:/projects/" + id;
    }

    public void changePriceSource(Project projectToUpdate, Long fromSource, Long toSource) {
        if (fromSource != toSource) {
            for (Room room : projectToUpdate.getRooms()) {
                for (Quantity quantity : room.getQuantities()) {
                    Task task = quantity.getTask();
                    Task alternativeTask = taskRepository.findTaskByWorkIdAndMaterialIdAndTypologyIdAndUserId(
                            task.getWork().getId(), task.getMaterial().getId(), task.getTypology().getId(), toSource);
                    task.updateSource(alternativeTask.getPrice(), alternativeTask.getPercentRange(), alternativeTask.getUnit());
                    quantity.setTask(alternativeTask);
                    quantityRepository.save(quantity);
                    task.getQuantities().remove(quantity);
                    taskRepository.save(task);
                }
                roomRepository.save(room);
            }
            projectToUpdate.setSourceId(toSource);
            projectRepository.save(projectToUpdate);
        }
    }
}
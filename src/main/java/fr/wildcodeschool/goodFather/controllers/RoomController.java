package fr.wildcodeschool.goodFather.controllers;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fr.wildcodeschool.goodFather.entities.Category;
import fr.wildcodeschool.goodFather.entities.Project;
import fr.wildcodeschool.goodFather.entities.Quantity;
import fr.wildcodeschool.goodFather.entities.Room;
import fr.wildcodeschool.goodFather.entities.Task;
import fr.wildcodeschool.goodFather.entities.Typology;
import fr.wildcodeschool.goodFather.repositories.CategoryRepository;
import fr.wildcodeschool.goodFather.repositories.ProjectRepository;
import fr.wildcodeschool.goodFather.repositories.QuantityRepository;
import fr.wildcodeschool.goodFather.repositories.RoomRepository;
import fr.wildcodeschool.goodFather.repositories.TaskRepository;
import fr.wildcodeschool.goodFather.repositories.TypologyRepository;

@Controller
public class RoomController {
    
    @Autowired
    RoomRepository roomRepository;

    @Autowired
    TypologyRepository typologyRepository;

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    QuantityRepository quantityRepository;
    
    @GetMapping("/rooms/create")
    public String showCreateRoom(Model model, @RequestParam Long projectId, @RequestParam Long categoryId) {
        if (projectId == null) {
            return "redirect:/projects";
        }
        else if (categoryId == null) {
            return "redirect:/projects/" + projectId + "/edit";
        }
        Category currentCategory = categoryRepository.findById(categoryId).get();
        Project currentProject = projectRepository.findById(projectId).get();
        model.addAttribute("category", currentCategory);
        model.addAttribute("project", currentProject);
        return"room-create";
    }

    @PostMapping("/rooms")
    public String create(@ModelAttribute Room room) {
        room = roomRepository.save(room);
        return "redirect:/rooms/" + room.getId() + "/edit";
    }

    @GetMapping("/rooms/{id}/edit")
    public String edit(@PathVariable("id") Long id, Model model){
        Room currentRoom = roomRepository.findById(id).get();
        Set<Typology> typologies = currentRoom.getCategory().getTypologies();
        List<Task> tasks = taskRepository.findAll();
        List<Typology> typologies2 = typologyRepository.findAll();
        model.addAttribute("project", currentRoom.getProject());
        model.addAttribute("room", currentRoom);
        model.addAttribute("tasks", tasks);
        model.addAttribute("typologies", typologies);
        model.addAttribute("typologies2", typologies2);
        model.addAttribute("quantities", currentRoom.getQuantities());
        return "tasks";
    }

    @DeleteMapping("/rooms/{id}")
    public String delete(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Room room = roomRepository.findById(id).get();
        Long projectId = room.getProject().getId();
        roomRepository.deleteById(id);
        redirectAttributes.addAttribute("message", "delete");
        return "redirect:/projects/"+projectId;
    }

    @DeleteMapping("/rooms/{id}/edit")
    public String deleteUserTask(@PathVariable Long id, RedirectAttributes redirectAttributes, @RequestParam Quantity quantity){
        Task task = quantity.getTask();
        Room room = quantity.getRoom();
        Project project = room.getProject();
        room.reduceCost(task.getPrice() * quantity.getQuantity(), task.getPercentRange());
        project.reduceCost(task.getPrice() * quantity.getQuantity(), task.getPercentRange());
        room = roomRepository.save(room);
        project = projectRepository.save(project);
        quantityRepository.delete(quantity);
        return "redirect:/rooms/" + id + "/edit"; 
    }
}
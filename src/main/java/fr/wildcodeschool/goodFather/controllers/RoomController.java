package fr.wildcodeschool.goodFather.controllers;

import java.util.Collections;
import java.util.List;
import java.util.TreeSet;

import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
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

import fr.wildcodeschool.goodFather.entities.Category;
import fr.wildcodeschool.goodFather.entities.Project;
import fr.wildcodeschool.goodFather.entities.Room;
import fr.wildcodeschool.goodFather.entities.Task;
import fr.wildcodeschool.goodFather.entities.Typology;
import fr.wildcodeschool.goodFather.entities.User;
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
    public String edit(@PathVariable("id") Long id, Model model,
    Authentication authentication
    ){
        Room currentRoom = roomRepository.findById(id).get();
        Project projectToUpdate = currentRoom.getProject();
        User currentUser = (User)authentication.getPrincipal();
        Long userId = currentUser.getId();
        Long projectUserId = projectToUpdate.getUser().getId();
        if (userId.equals(projectUserId)) {
            TreeSet<Typology> typologies = new TreeSet<Typology>(currentRoom.getCategory().getTypologies());
            List<Task> tasks = taskRepository.findAll();
            Collections.sort(tasks);
            model.addAttribute("project", currentRoom.getProject());
            model.addAttribute("room", currentRoom);
            model.addAttribute("tasks", tasks);
            model.addAttribute("typologies", typologies);
            model.addAttribute("quantities", currentRoom.getQuantities());
            double surfaceA = Math.round((currentRoom.getWallA()/100) * (currentRoom.getHeight()/100) * 100.00) / 100.00;
            double surfaceB = Math.round((currentRoom.getWallB()/100) * (currentRoom.getHeight()/100) * 100.00) / 100.00;
            double ground = Math.round((currentRoom.getWallA()/100) * (currentRoom.getWallB()/100) * 100.00) / 100.00;
            model.addAttribute("surfaceA", surfaceA);
            model.addAttribute("surfaceB", surfaceB);
            model.addAttribute("ground", ground);
            return "tasks";
        }
        return"error";
    }

    @DeleteMapping("/rooms/{id}")
    public String delete(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Room room = roomRepository.findById(id).get();
        Project project = room.getProject();
        project.removeRoomCost(room);
        project = projectRepository.save(project);
        roomRepository.deleteById(id);
        redirectAttributes.addAttribute("message", "delete");
        return "redirect:/projects/"+project.getId();
    }

    @PutMapping("rooms/{id}")
    public String updateComment(@PathVariable Long id, Room room, RedirectAttributes redirectAttributes) {
        Room roomToUpdate = roomRepository.findById(id).get();
        roomToUpdate.setComment(room.getComment());
        roomRepository.save(roomToUpdate);
        redirectAttributes.addAttribute("message", "edit");
        return "redirect:/rooms/" + room.getId() + "/edit";
    }
}
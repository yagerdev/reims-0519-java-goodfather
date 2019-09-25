package fr.wildcodeschool.goodFather.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fr.wildcodeschool.goodFather.entities.Project;
import fr.wildcodeschool.goodFather.entities.Quantity;
import fr.wildcodeschool.goodFather.entities.Room;
import fr.wildcodeschool.goodFather.entities.Task;
import fr.wildcodeschool.goodFather.repositories.ProjectRepository;
import fr.wildcodeschool.goodFather.repositories.QuantityRepository;
import fr.wildcodeschool.goodFather.repositories.RoomRepository;

@Controller
public class QuantityController {

    @Autowired
    private QuantityRepository quantityRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @DeleteMapping("/quantity")
    public String removeTask(RedirectAttributes redirectAttributes, @RequestParam Quantity quantity){
        Task task = quantity.getTask();
        Room room = quantity.getRoom();
        Project project = room.getProject();
        room.reduceCost(task.getPrice() * quantity.getQuantity(), task.getPercentRange());
        project.reduceCost(task.getPrice() * quantity.getQuantity(), task.getPercentRange());
        room = roomRepository.save(room);
        project = projectRepository.save(project);
        quantityRepository.delete(quantity);
        return "redirect:/rooms/" + room.getId() + "/edit"; 
    }
}
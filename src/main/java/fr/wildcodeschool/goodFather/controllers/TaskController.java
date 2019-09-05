package fr.wildcodeschool.goodFather.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.wildcodeschool.goodFather.entities.Material;
import fr.wildcodeschool.goodFather.entities.Task;
import fr.wildcodeschool.goodFather.entities.Typology;
import fr.wildcodeschool.goodFather.entities.Work;
import fr.wildcodeschool.goodFather.repositories.MaterialRepository;
import fr.wildcodeschool.goodFather.repositories.TaskRepository;
import fr.wildcodeschool.goodFather.repositories.TypologyRepository;
import fr.wildcodeschool.goodFather.repositories.WorkRepository;

@Controller
public class TaskController {
    
    @Autowired
    TaskRepository taskRepository;

    @Autowired
    TypologyRepository typologyRepository;

    @Autowired
    MaterialRepository materialRepository;

    @Autowired
    WorkRepository workRepository;

    @GetMapping("/tasks")
    public String showCreateTask(Model model) {
        List<Task> taskList = taskRepository.findAll();
        model.addAttribute("tasks", taskList);
        return "admin-task";
    }

    @PostMapping("/tasks")
    public String createTask(
        @RequestParam("typology") Long typologyId,
        @RequestParam("work") Long workId,
        @RequestParam("material") Long materialId,
        @RequestParam("price") double price,
        @RequestParam("percent_range") double percentRange,
        @RequestParam("unit") String unit
        ) {
            Typology typology = typologyRepository.getOne(typologyId);
            Material material = materialRepository.getOne(materialId);
            Work work = workRepository.getOne(workId);
            Task task = new Task(price, unit, percentRange, typology, material, work);
            taskRepository.save(task);
            return "redirect:/tasks";
    }
}
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
        List<Task> tasks = taskRepository.findAll();
        List<Work> works = workRepository.findAll();
        List<Material> materials = materialRepository.findAll();
        model.addAttribute("tasks", tasks);
        model.addAttribute("works", works);
        model.addAttribute("materials", materials);

        return "admin/task";
    }

    @PostMapping("/tasks")
    public String createTask(Model model,
        @RequestParam(required = false) Long workId,
        @RequestParam(required = false) Long materialId,
        @RequestParam(required = false) Double price,
        @RequestParam(required = false) String unit, 
        @RequestParam(required = false) Double percentRange) {
            
            if(workId == null || materialId == null || price == null) {
                return "redirect:/tasks";
            }
            else {
                Material material = materialRepository.getOne(materialId);
                Work work = workRepository.getOne(workId);
                Task task = new Task(price, unit, percentRange, material, work);
                task = taskRepository.save(task);
                return "redirect:/tasks";
            }
    }
}
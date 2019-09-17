package fr.wildcodeschool.goodFather.controllers;

import java.util.List;

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

import fr.wildcodeschool.goodFather.entities.Material;
import fr.wildcodeschool.goodFather.entities.Room;
import fr.wildcodeschool.goodFather.entities.Task;
import fr.wildcodeschool.goodFather.entities.Work;
import fr.wildcodeschool.goodFather.repositories.MaterialRepository;
import fr.wildcodeschool.goodFather.repositories.RoomRepository;
import fr.wildcodeschool.goodFather.repositories.TaskRepository;
import fr.wildcodeschool.goodFather.repositories.WorkRepository;

@Controller
public class TaskController {
    
    @Autowired
    TaskRepository taskRepository;

    @Autowired
    MaterialRepository materialRepository;

    @Autowired
    WorkRepository workRepository;

    @Autowired
    RoomRepository roomRepository;

    @GetMapping("/tasks")
    public String showCreateTask(Model model, @RequestParam(value = "message", required = false) String message) {
        List<Task> tasks = taskRepository.findAll();
        List<Work> works = workRepository.findAll();
        List<Material> materials = materialRepository.findAll();
        model.addAttribute("tasks", tasks);
        model.addAttribute("works", works);
        model.addAttribute("materials", materials);
        model.addAttribute("message", message);
        return "admin/task";
    }

    @PostMapping("/tasks")
    public String create(
        Model model,
        @RequestParam(required = false) Long workId,
        @RequestParam(required = false) Long materialId,
        @RequestParam(required = false) Double price,
        @RequestParam(required = false) String unit, 
        @RequestParam(required = false) Double percentRange,
        RedirectAttributes redirectAttributes
    ) {
        if(workId == null || materialId == null || price == null || unit == null) {
            redirectAttributes.addAttribute("message", "invalide");
            return "redirect:/tasks";
        }
        else {
            if (taskRepository.findTaskByWorkIdAndMaterialId(workId, materialId) == null) {
                Material material = materialRepository.getOne(materialId);
                Work work = workRepository.getOne(workId);
                Task task = new Task(price, unit, percentRange, material, work);
                task = taskRepository.save(task);
                redirectAttributes.addAttribute("message", "success");
            }
            redirectAttributes.addAttribute("message", "doublon");
            return "redirect:/tasks";
        }
    }

    @PostMapping("/tasks/add")
    public String add(
        @ModelAttribute Room room,
        @ModelAttribute Work work,
        @ModelAttribute Material material
    ) {
        Task task = taskRepository.findTaskByWorkIdAndMaterialId(work.getId(), material.getId());
        room.addTask(task);
        room = roomRepository.save(room);
        return "redirect:/rooms/" + room.getId() + "/edit";
    }

    @DeleteMapping("/tasks/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes){
        taskRepository.deleteById(id);
        redirectAttributes.addAttribute("message", "delete");
        return "redirect:/tasks";
    }
}
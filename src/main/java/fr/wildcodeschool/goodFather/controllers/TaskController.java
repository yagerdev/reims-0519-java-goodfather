package fr.wildcodeschool.goodFather.controllers;

import java.util.Collections;
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
import fr.wildcodeschool.goodFather.entities.Project;
import fr.wildcodeschool.goodFather.entities.Quantity;
import fr.wildcodeschool.goodFather.entities.Room;
import fr.wildcodeschool.goodFather.entities.Task;
import fr.wildcodeschool.goodFather.entities.Typology;
import fr.wildcodeschool.goodFather.entities.Work;
import fr.wildcodeschool.goodFather.repositories.MaterialRepository;
import fr.wildcodeschool.goodFather.repositories.ProjectRepository;
import fr.wildcodeschool.goodFather.repositories.QuantityRepository;
import fr.wildcodeschool.goodFather.repositories.RoomRepository;
import fr.wildcodeschool.goodFather.repositories.TaskRepository;
import fr.wildcodeschool.goodFather.repositories.TypologyRepository;
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

    @Autowired
    QuantityRepository quantityRepository;

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    TypologyRepository typologyRepository;

    @GetMapping("/tasks")
    public String show(Model model, @RequestParam(value = "message", required = false) String message) {
        List<Task> tasks = taskRepository.findAll();
        List<Typology> typologies = typologyRepository.findAll();
        List<Work> works = workRepository.findAll();
        List<Material> materials = materialRepository.findAll();
        Collections.sort(tasks);
        Collections.sort(typologies);
        Collections.sort(works);
        Collections.sort(materials);
        model.addAttribute("tasks", tasks);
        model.addAttribute("typologies", typologies);
        model.addAttribute("works", works);
        model.addAttribute("materials", materials);
        model.addAttribute("message", message);
        return "admin/task";
    }

    @GetMapping("/tasks/{id}")
    public String read(@PathVariable Long id, Model model) {
        Task task = taskRepository.findById(id).get();
        model.addAttribute("task", task);
        return "admin/task-edit";
    }

    @PostMapping("/tasks")
    public String create(
        Model model,
        @RequestParam(required = false) Long workId,
        @RequestParam(required = false) Long materialId,
        @RequestParam(required = false) Long typologyId,
        @RequestParam(required = false) Double price,
        @RequestParam(required = false) String unit, 
        @RequestParam(required = false) Double percentRange,
        RedirectAttributes redirectAttributes
    ) {
        if(workId == null || materialId == null || typologyId == null || price == null || unit == null) {
            redirectAttributes.addAttribute("message", "invalide");
        }
        else {
            if (taskRepository.findTaskByWorkIdAndMaterialIdAndTypologyId(workId, materialId, typologyId) == null) {
                Material material = materialRepository.getOne(materialId);
                Work work = workRepository.getOne(workId);
                Typology typology = typologyRepository.getOne(typologyId);
                Task task = new Task(price, unit, percentRange, typology, material, work);
                task = taskRepository.save(task);
                redirectAttributes.addAttribute("message", "success");
            } else {
                redirectAttributes.addAttribute("message", "doublon");
            }
        }
        return "redirect:/tasks";
    }

    @PostMapping("/task/add")
    public String add(
        @ModelAttribute Typology typology,
        @ModelAttribute Room room,
        @ModelAttribute Work work,
        @ModelAttribute Material material,
        @RequestParam double quantityValue,
        RedirectAttributes redirectAttributes
    ) {
        Task task = taskRepository.findTaskByWorkIdAndMaterialIdAndTypologyId(work.getId(), material.getId(), typology.getId());
        Quantity quantity = new Quantity(room, task, quantityValue);
        if (quantityRepository.findQuantityByRoomIdAndTaskId(room.getId(), task.getId()) == null) {
            Project project = room.getProject();
            quantity = quantityRepository.save(quantity);
            room.addCost(task.getPrice()*quantity.getQuantity(), task.getPercentRange());
            room = roomRepository.save(room);
            project.addCost(task.getPrice()*quantity.getQuantity(), task.getPercentRange());
            project = projectRepository.save(project);
        }
        redirectAttributes.addAttribute("message", "success");
        return "redirect:/rooms/" + room.getId() + "/edit";
    }

    @DeleteMapping("/tasks/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        taskRepository.deleteById(id);
        redirectAttributes.addAttribute("message", "delete");
        return "redirect:/tasks";
    }

    @PutMapping("/tasks/{id}")
    public String update(@PathVariable Long id, Task task, RedirectAttributes redirectAttributes) {
        Task taskToUpdate = taskRepository.findById(id).get();
        taskToUpdate.setPercentRange(task.getPercentRange());
        taskToUpdate.setPrice(task.getPrice());
        taskToUpdate.setUnit(task.getUnit());
        taskRepository.save(taskToUpdate);
        redirectAttributes.addAttribute("message", "edit");
        return "redirect:/tasks";
    }
}
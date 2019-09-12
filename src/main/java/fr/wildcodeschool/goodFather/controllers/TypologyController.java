package fr.wildcodeschool.goodFather.controllers;

import fr.wildcodeschool.goodFather.entities.Task;
import fr.wildcodeschool.goodFather.entities.Typology;
import fr.wildcodeschool.goodFather.repositories.TaskRepository;
import fr.wildcodeschool.goodFather.repositories.TypologyRepository;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TypologyController {

    @Autowired
    TypologyRepository typologyRepository;

    @Autowired
    TaskRepository taskRepository;

    @GetMapping("/typologies")
    public String show(Model model) {
        List<Typology> typologyList = typologyRepository.findAll();
        model.addAttribute("typologies", typologyList);
        return "admin/typology";
    }

    @GetMapping("/typologies/{id}/edit")
    public String readTypology(Model model, @PathVariable Long id) {
        Typology typology = typologyRepository.findById(id).get();
        List<Task> tasks = taskRepository.findAll();
        Map<Task, Boolean> checked = new HashMap<Task, Boolean>();
        for (Task task : tasks) {
            if (typology.getTasks().contains(task)) {
                checked.put(task, true);
            } else {
                checked.put(task, false);
            }
        }
        model.addAttribute("entityName", typology.getName());
        model.addAttribute("entityType", "typologies");
        model.addAttribute("entityId", id);
        model.addAttribute("listType", "tasks");
        model.addAttribute("myMap", checked);
        return "admin/config";
    }

    @PutMapping("/typologies/{id}")
    public String editTypology(
            @PathVariable(name = "id") Long typologyId,
            @RequestParam(required = false, name = "tasks") List<Long> taskIds,
            Typology typology
    ) {
        Typology typologyToUpdate = typologyRepository.findById(typologyId).get();
        if (taskIds == null) {
            typologyToUpdate.setName(typology.getName());
        } else {
            Set<Task> tasks = new HashSet<Task>();
            for (Long taskId : taskIds) {
                tasks.add( taskRepository.findById( taskId ).get() );
            }
            typologyToUpdate.setTasks(tasks);
        }
        typologyRepository.save(typologyToUpdate);
        return "redirect:/typologies";
    }

    @PostMapping("/typologies")
    public String create(@RequestParam String name) {
        Typology typology = new Typology(name);
        typologyRepository.save(typology);
        return "redirect:/typologies";
    }

    @DeleteMapping("/typologies/{id}")
    public String delete(@PathVariable Long id) {
        typologyRepository.deleteById(id);
        return "redirect:/typologies";
    }
}
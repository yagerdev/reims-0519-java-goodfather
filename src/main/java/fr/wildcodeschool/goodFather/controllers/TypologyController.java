package fr.wildcodeschool.goodFather.controllers;

import fr.wildcodeschool.goodFather.entities.Task;
import fr.wildcodeschool.goodFather.entities.Typology;
import fr.wildcodeschool.goodFather.repositories.TaskRepository;
import fr.wildcodeschool.goodFather.repositories.TypologyRepository;

import java.util.Collections;
import java.util.HashSet;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class TypologyController {

    @Autowired
    TypologyRepository typologyRepository;

    @Autowired
    TaskRepository taskRepository;

    @GetMapping("/typologies")
    public String show(Model model, @RequestParam(required = false) String message) {
        List<Typology> typologyList = typologyRepository.findAll();
        Collections.sort(typologyList);
        model.addAttribute("message", message);
        model.addAttribute("typologies", typologyList);
        return "admin/typology";
    }

    @PutMapping("/typologies/{id}")
    public String editTypology(
        @PathVariable(name = "id") Long typologyId,
        @RequestParam(required = false, name = "tasks") List<Long> taskIds,
        Typology typology,
        RedirectAttributes redirectAttributes
    ) {
        Typology typologyToUpdate = typologyRepository.findById(typologyId).get();
        if (typology.getName() != null) {
            typologyToUpdate.setName(typology.getName());
            redirectAttributes.addAttribute("message", "edit");
        } else {
            Set<Task> tasks = new HashSet<Task>();
            if (taskIds != null) {
                for (Long taskId : taskIds) {
                    tasks.add( taskRepository.findById( taskId ).get() );
                }
            }
            typologyToUpdate.setTasks(tasks);
        }
        typologyRepository.save(typologyToUpdate);
        return "redirect:/typologies";
    }

    @PostMapping("/typologies")
    public String create(@ModelAttribute Typology typology, RedirectAttributes redirectAttributes) {
        typologyRepository.save(typology);
        redirectAttributes.addAttribute("message", "success");
        return "redirect:/typologies";
    }

    @DeleteMapping("/typologies/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        typologyRepository.deleteById(id);
        redirectAttributes.addAttribute("message", "delete");
        return "redirect:/typologies";
    }
}
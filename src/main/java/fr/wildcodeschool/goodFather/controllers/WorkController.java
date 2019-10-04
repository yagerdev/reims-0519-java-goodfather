package fr.wildcodeschool.goodFather.controllers;

import fr.wildcodeschool.goodFather.entities.Work;
import fr.wildcodeschool.goodFather.repositories.WorkRepository;

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

@Controller
public class WorkController {

    @Autowired
    WorkRepository workRepository;

    @GetMapping("/works")
    public String show(Model model, @RequestParam(value = "message", required = false) String message) {
        List<Work> workList = workRepository.findAll();
        Collections.sort(workList);
        model.addAttribute("works", workList);
        model.addAttribute("message", message);
        return "admin/work";
    }

    @PostMapping("/works")
    public String create(@ModelAttribute Work work, RedirectAttributes redirectAttributes) {
        if (workRepository.findByName(work.getName()) == null) {
            workRepository.save(work);
            redirectAttributes.addAttribute("message", "success");
        } else {
            redirectAttributes.addAttribute("message", "doublon");
        }
        return "redirect:/works";
    }

    @PutMapping("/works/{id}")
    public String update(@PathVariable Long id, Work work, RedirectAttributes redirectAttributes) {
        Work workToUpdate = workRepository.findById(id).get();
        workToUpdate.setName(work.getName());
        workRepository.save(workToUpdate);
        redirectAttributes.addAttribute("message", "edit");
        return "redirect:/works";
    }

    @DeleteMapping("/works/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes){
        workRepository.deleteById(id);
        redirectAttributes.addAttribute("message", "delete");
        return "redirect:/works";
    }
}
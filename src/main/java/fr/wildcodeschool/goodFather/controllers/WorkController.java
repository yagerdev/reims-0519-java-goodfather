package fr.wildcodeschool.goodFather.controllers;

import fr.wildcodeschool.goodFather.entities.Work;
import fr.wildcodeschool.goodFather.repositories.WorkRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WorkController {

    @Autowired
    WorkRepository workRepository;

    @GetMapping("/works")
    public String showCategories(Model model) {
        List<Work> workList = workRepository.findAll();
        model.addAttribute("works", workList);
        return "admin/work";
    }

    @PostMapping("/works")
    public String createWork(@RequestParam String name) {
        Work work = new Work(name);
        workRepository.save(work);
        return "redirect:/works";
    }
}
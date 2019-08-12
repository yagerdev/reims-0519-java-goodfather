package fr.wildcodeschool.goodFather.controllers;

import fr.wildcodeschool.goodFather.entities.Typology;
import fr.wildcodeschool.goodFather.repositories.TypologyRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TypologyController {

    @Autowired
    TypologyRepository typologyRepository;

    @GetMapping("/typologies")
    public String showTypologies(Model model) {
        //Todo : check if user is admin
        List<Typology> typologyList = typologyRepository.findAll();
        model.addAttribute("typologies", typologyList);
        return "admin-typology";
    }

    @PostMapping("/typologies")
    public String createTypology(@RequestParam String name) {
        //Todo : check if user is admin
        Typology typology = new Typologie(name);
        typologyRepository.save(typology);
        return "redirect:/typologies";
    }
}
package fr.wildcodeschool.goodFather.controllers;

import fr.wildcodeschool.goodFather.entities.Typology;
import fr.wildcodeschool.goodFather.repositories.TypologyRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TypologyController {

    @Autowired
    TypologyRepository typologyRepository;

    @GetMapping("/typologies")
    public String showTypologies() {
        //Todo : check if user is admin
        return "admin-typology";
    }

    @PostMapping("/typologies")
    public String createTypology(@RequestParam String name) {
        //Todo : check if user is admin
        Typology typology = new Typology(name);
        typologyRepository.save(typology);
        return "redirect:/typologies";
    }
}
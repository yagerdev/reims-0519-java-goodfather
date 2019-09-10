package fr.wildcodeschool.goodFather.controllers;

import fr.wildcodeschool.goodFather.entities.Typology;
import fr.wildcodeschool.goodFather.repositories.TypologyRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TypologyController {

    @Autowired
    TypologyRepository typologyRepository;

    @GetMapping("/typologies")
    public String showTypologies(Model model) {
        List<Typology> typologyList = typologyRepository.findAll();
        model.addAttribute("typologies", typologyList);
        return "admin/typology";
    }

    @PostMapping("/typologies")
    public String createTypology(@RequestParam String name) {
        Typology typology = new Typology(name);
        typologyRepository.save(typology);
        return "redirect:/typologies";
    }

    @DeleteMapping("/typologies/{id}")
    public String deleteTypology(@PathVariable Long id){
        typologyRepository.deleteById(id);
        return "redirect:/typologies";
    }
}
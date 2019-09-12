package fr.wildcodeschool.goodFather.controllers;

import fr.wildcodeschool.goodFather.entities.Material;
import fr.wildcodeschool.goodFather.repositories.MaterialRepository;

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

@Controller
public class MaterialController {

    @Autowired
    MaterialRepository materialRepository;

    @GetMapping("/materials")
    public String show(Model model) {
        List<Material> materialList = materialRepository.findAll();
        model.addAttribute("materials", materialList);
        return "admin/material";
    }

    @PostMapping("/materials")
    public String create(@ModelAttribute Material material) {
        materialRepository.save(material);
        return "redirect:/materials";
    }

    @PutMapping("/materials/{id}")
    public String update(@PathVariable Long id, Material material) {
        Material materialToUpdate = materialRepository.findById(id).get();
        materialToUpdate.setName(material.getName());
        materialRepository.save(materialToUpdate);
        return "redirect:/materials";
    }

    @DeleteMapping("/materials/{id}")
    public String delete(@PathVariable Long id){
        materialRepository.deleteById(id);
        return "redirect:/materials";
    }
}
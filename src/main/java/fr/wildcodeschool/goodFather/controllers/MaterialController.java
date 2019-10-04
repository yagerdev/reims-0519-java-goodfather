package fr.wildcodeschool.goodFather.controllers;

import fr.wildcodeschool.goodFather.entities.Material;
import fr.wildcodeschool.goodFather.repositories.MaterialRepository;

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
public class MaterialController {

    @Autowired
    MaterialRepository materialRepository;

    @GetMapping("/materials")
    public String show(Model model, @RequestParam(value = "message", required = false) String message) {
        List<Material> materialList = materialRepository.findAll();
        Collections.sort(materialList);
        model.addAttribute("materials", materialList);
        model.addAttribute("message", message);
        return "admin/material";
    }

    @PostMapping("/materials")
    public String create(@ModelAttribute Material material, RedirectAttributes redirectAttributes) {
        materialRepository.save(material);
        redirectAttributes.addAttribute("message", "success");
        return "redirect:/materials";
    }

    @PutMapping("/materials/{id}")
    public String update(@PathVariable Long id, Material material, RedirectAttributes redirectAttributes) {
        Material materialToUpdate = materialRepository.findById(id).get();
        materialToUpdate.setName(material.getName());
        materialRepository.save(materialToUpdate);
        redirectAttributes.addAttribute("message", "edit");
        return "redirect:/materials";
    }

    @DeleteMapping("/materials/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes){
        materialRepository.deleteById(id);
        redirectAttributes.addAttribute("message", "delete");
        return "redirect:/materials";
    }
}
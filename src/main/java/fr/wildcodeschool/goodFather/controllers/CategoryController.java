package fr.wildcodeschool.goodFather.controllers;

import fr.wildcodeschool.goodFather.entities.Category;
import fr.wildcodeschool.goodFather.entities.Typology;
import fr.wildcodeschool.goodFather.repositories.CategoryRepository;
import fr.wildcodeschool.goodFather.repositories.TypologyRepository;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

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
public class CategoryController {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    TypologyRepository typologyRepository;

    @GetMapping("/categories")
    public String show(Model model, @RequestParam(value = "message", required = false) String message) {
        List<Category> categoryList = categoryRepository.findAll();
        Collections.sort(categoryList);
        model.addAttribute("categories", categoryList);
        model.addAttribute("message",message);
        return "admin/category";
    }

    @GetMapping("/categories/{id}/edit")
    public String read(Model model, @PathVariable Long id) {
        Category category = categoryRepository.findById(id).get();
        List<Typology> typologies = typologyRepository.findAll();
        Map<Typology, Boolean> checked = new TreeMap<Typology, Boolean>();
        for (Typology typology : typologies) {
            if (category.getTypologies().contains(typology)) {
                checked.put(typology, true);
            } else {
                checked.put(typology, false);
            }
        }
        model.addAttribute("categoryName", category.getName());
        model.addAttribute("categoryId", id);
        model.addAttribute("myMap", checked);
        return "admin/config";
    }

    @PutMapping("/categories/{id}")
    public String edit(
        @PathVariable(name = "id") Long categoryId,
        @RequestParam(required = false, name = "typologies") List<Long> typologyIds,
        Category category,
        RedirectAttributes redirectAttributes
    ) {
        Category categoryToUpdate = categoryRepository.findById(categoryId).get();
        if (category.getName() != null) {
            categoryToUpdate.setName(category.getName());
            redirectAttributes.addAttribute("message", "edit");
        } else {
            Set<Typology> typologies = new HashSet<Typology>();
            if (typologyIds != null) {
                for (Long typologyId : typologyIds) {
                    typologies.add(typologyRepository.findById(typologyId).get());
                }
            }
            categoryToUpdate.setTypologies(typologies);
        }
        categoryRepository.save(categoryToUpdate);
        return "redirect:/categories";
    }

    @PostMapping("/categories")
    public String create(@ModelAttribute Category category, RedirectAttributes redirectAttributes) {
        if (categoryRepository.findByName(category.getName()) == null) {
            categoryRepository.save(category);
            redirectAttributes.addAttribute("message", "success");
        } else {
            redirectAttributes.addAttribute("message", "doublon");
        }
        return "redirect:/categories";
    }

    @DeleteMapping("/categories/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        categoryRepository.deleteById(id);
        redirectAttributes.addAttribute("message", "delete");
        return "redirect:/categories";
    }

}
package fr.wildcodeschool.goodFather.controllers;

import fr.wildcodeschool.goodFather.entities.Category;
import fr.wildcodeschool.goodFather.entities.Typology;
import fr.wildcodeschool.goodFather.repositories.CategoryRepository;
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
public class CategoryController {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    TypologyRepository typologyRepository;

    @GetMapping("/categories")
    public String show(Model model) {
        List<Category> categoryList = categoryRepository.findAll();
        model.addAttribute("categories", categoryList);
        return "admin/category";
    }

    @GetMapping("/categories/{id}/edit")
    public String read(Model model, @PathVariable Long id) {
        Category category = categoryRepository.findById(id).get();
        List<Typology> typologies = typologyRepository.findAll();
        Map<Typology, Boolean> checked = new HashMap<Typology, Boolean>();
        for (Typology typology : typologies) {
            if (category.getTypologies().contains(typology)) {
                checked.put(typology, true);
            } else {
                checked.put(typology, false);
            }
        }
        model.addAttribute("entityName", category.getName());
        model.addAttribute("entityType", "categories");
        model.addAttribute("entityId", id);
        model.addAttribute("listType", "typologies");
        model.addAttribute("myMap", checked);
        return "admin/config";
    }

    @PutMapping("/categories/{id}")
    public String edit(@PathVariable(name = "id") Long categoryId,
            @RequestParam(required = false, name = "typologies") List<Long> typologyIds) {
        Category category = categoryRepository.findById(categoryId).get();
        Set<Typology> typologies = new HashSet<Typology>();
        if (typologyIds != null) {
            for (Long typologyId : typologyIds) {
                typologies.add( typologyRepository.findById( typologyId ).get() );
            }
        }
        category.setTypologies(typologies);
        categoryRepository.save(category);
        return "redirect:/categories";
    }

    @PostMapping("/categories")
    public String create(@RequestParam String name) {
        Category category = new Category(name);
        categoryRepository.save(category);
        return "redirect:/categories";
    }

    @PutMapping("/categories/{id}")
    public String update(@PathVariable Long id, Category category) {
        Category categoryToUpdate = categoryRepository.findById(id).get();
        categoryToUpdate.setName(category.getName());
        categoryRepository.save(categoryToUpdate);
        return "redirect:/categories";
    }

    @DeleteMapping("/categories/{id}")
    public String delete(@PathVariable Long id){
        categoryRepository.deleteById(id);
        return "redirect:/categories";
    }

}
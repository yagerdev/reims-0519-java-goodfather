package fr.wildcodeschool.goodFather.controllers;

import fr.wildcodeschool.goodFather.entities.Category;
import fr.wildcodeschool.goodFather.entities.Typology;
import fr.wildcodeschool.goodFather.repositories.CategoryRepository;
import fr.wildcodeschool.goodFather.repositories.TypologyRepository;

import java.util.HashSet;
import java.util.List;
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
    public String showCategories(Model model) {
        List<Category> categoryList = categoryRepository.findAll();
        model.addAttribute("categories", categoryList);
        return "admin/category";
    }

    @GetMapping("/categories/{id}/edit")
    public String readCategory(Model model, @PathVariable Long id) {
        Category category = categoryRepository.findById(id).get();
        List<Typology> typologies = typologyRepository.findAll();
        model.addAttribute("entityName", category.getName());
        model.addAttribute("entityType", "categories");
        model.addAttribute("entityId", id);
        model.addAttribute("listType", "typologies");
        model.addAttribute("myList", typologies);
        return "admin/config";
    }

    @PutMapping("/categories/{id}")
    public String editCategory(@PathVariable(name = "id") Long categoryId, @RequestParam(name = "typologies") List<Long> typologyIds) {
        Set<Typology> typologies = new HashSet<Typology>();
        for (Long typologyId : typologyIds) {
            Typology typology = typologyRepository.findById(typologyId).get();
            typologies.add(typology);
        }
        Category category = categoryRepository.findById(categoryId).get();
        category.setTypologies(typologies);
        categoryRepository.save(category);
        return "redirect:/categories";
    }

    @PostMapping("/categories")
    public String createCategory(@RequestParam String name) {
        Category category = new Category(name);
        categoryRepository.save(category);
        return "redirect:/categories";
    }

    @DeleteMapping("/categories/{id}")
    public String deleteCategory(@PathVariable Long id){
        categoryRepository.deleteById(id);
        return "redirect:/categories";
    }
    
}
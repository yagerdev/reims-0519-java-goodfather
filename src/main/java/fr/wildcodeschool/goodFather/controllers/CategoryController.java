package fr.wildcodeschool.goodFather.controllers;

import fr.wildcodeschool.goodFather.entities.Category;
import fr.wildcodeschool.goodFather.repositories.CategoryRepository;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

public class CategoryController {

    @GetMapping("/categories")
    public String showCategories() {
        //Todo : check if user is admin
        return "categories";
    }

    @PostMapping("/categories")
    public String createCategory(@RequestParam String name) {
        //Todo : check if user is admin
        Category category = new Category(name);
        CategoryRepository.save(category);
        return "redirect:/categories";
    }
}
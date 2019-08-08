package fr.wildcodeschool.goodFather.controllers;

import fr.wildcodeschool.goodFather.entities.Category;
import fr.wildcodeschool.goodFather.repositories.CategoryRepository;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

public class CategoryController {

    @GetMapping("/admin-category")
    public String showAdminCategory() {
        return "admin-category";
    }

    @PostMapping("/admin-category")
    public String createCategory(@RequestParam String name) {
        Category category = new Category(name);
        CategoryRepository.save(category);
        return "redirect:/admin-category";
    }
}
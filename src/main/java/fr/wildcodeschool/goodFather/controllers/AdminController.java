package fr.wildcodeschool.goodFather.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import fr.wildcodeschool.goodFather.entities.User;

@Controller
public class AdminController {

    @GetMapping("/admin")
    public String getHomeAdmin(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User)authentication.getPrincipal();
        model.addAttribute("user", currentUser);
        return "admin/home";
    }

}
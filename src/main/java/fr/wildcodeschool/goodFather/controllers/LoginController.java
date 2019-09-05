package fr.wildcodeschool.goodFather.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import fr.wildcodeschool.goodFather.entities.User;
import fr.wildcodeschool.goodFather.repositories.UserRepository;

@Controller
public class LoginController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/login")
    public String login() {
        return "redirect:/";
    }

    @GetMapping("/home")
    public String redirectByRole() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User)authentication.getPrincipal();
        if (currentUser.getRole().equals("ADMIN")) {
            return "redirect:/admin";
        }
        else {
            return "redirect:/projects/create";
        }
    }

}
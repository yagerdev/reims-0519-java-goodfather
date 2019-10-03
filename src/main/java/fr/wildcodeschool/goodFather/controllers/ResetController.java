package fr.wildcodeschool.goodFather.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fr.wildcodeschool.goodFather.entities.User;
import fr.wildcodeschool.goodFather.repositories.UserRepository;
@Controller
public class ResetController{

    @Autowired
    UserRepository userRepository;

    @GetMapping("/reset/{id}")
    public String edit(
        @PathVariable Long id,
        User user
    ) {
        return "admin/reset-password";
    }

    @PutMapping("/reset/{id}")
    public String resetPassword(
        User user,
        @PathVariable Long id,
        @RequestParam("password") String password,
        RedirectAttributes redirectAttributes
        ) {
        User changeUserPass = userRepository.findById(id).get();

        changeUserPass.setPassword(password);
        userRepository.save(changeUserPass);
        redirectAttributes.addAttribute("message", "edit");
        return"redirect:/users/"+id;
    }
}
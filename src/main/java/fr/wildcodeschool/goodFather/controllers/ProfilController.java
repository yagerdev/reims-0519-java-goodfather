package fr.wildcodeschool.goodFather.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import fr.wildcodeschool.goodFather.entities.User;
import fr.wildcodeschool.goodFather.repositories.ProjectRepository;
import fr.wildcodeschool.goodFather.repositories.UserRepository;

@Controller
public class ProfilController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProjectRepository projectRepository;
    
    @GetMapping("/profil")
    public String loadProfil( Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User users = (User)authentication.getPrincipal();
        model.addAttribute("user", users);
        return "profil";
    }

    @PutMapping("/profil/{id}")
    public String update(@PathVariable Long id, User user) {
        User userToUpdate = userRepository.findById(id).get();
        userToUpdate.setFirstName(user.getFirstName());
        userToUpdate.setLastName(user.getLastName());
        userToUpdate.setEmail(user.getEmail());
        userToUpdate.setPhoneNumber(user.getPhoneNumber());
        userToUpdate.setAddress(user.getAddress());
        userToUpdate.setCity(user.getCity());
        userToUpdate.setPostalCode(user.getPostalCode());
        userRepository.save(userToUpdate);
        return "redirect:/profil";
    }

}
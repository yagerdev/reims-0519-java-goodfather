package fr.wildcodeschool.goodFather.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import fr.wildcodeschool.goodFather.entities.User;
import fr.wildcodeschool.goodFather.repositories.ProjectRepository;
import fr.wildcodeschool.goodFather.repositories.UserRepository;

@Controller
public class ProfileController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProjectRepository projectRepository;

    @GetMapping("/profile")
    public String loadProfile(
        Model model, 
        Authentication authentication,
        @RequestParam(value = "message", required = false) String message
    ) {
        User userToUpdate = (User) authentication.getPrincipal();
        model.addAttribute("user", userToUpdate);
        model.addAttribute("message", message);
        return "profile";
    }

    @PutMapping("/profile")
    public String update(
        User user,
        RedirectAttributes redirectAttributes,
        Authentication authentication
    ) {
        User userToUpdate = (User) authentication.getPrincipal();
        userToUpdate.setFirstName(user.getFirstName());
        userToUpdate.setLastName(user.getLastName());
        userToUpdate.setEmail(user.getEmail());
        userToUpdate.setPhoneNumber(user.getPhoneNumber());
        userToUpdate.setAddress(user.getAddress());
        userToUpdate.setCity(user.getCity());
        userToUpdate.setPostalCode(user.getPostalCode());
        userToUpdate = userRepository.save(userToUpdate);
        redirectAttributes.addAttribute("user", userToUpdate);
        redirectAttributes.addAttribute("message", "edit");
        return "redirect:/profile";
    }

}

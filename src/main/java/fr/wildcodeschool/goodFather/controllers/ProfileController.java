package fr.wildcodeschool.goodFather.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
        @RequestParam(value = "message", required = false) String message, 
        @RequestParam(value = "emailError", required = false) String email
    ) {
        User userToUpdate = (User) authentication.getPrincipal();
        model.addAttribute("user", userToUpdate);
        model.addAttribute("userToUpdate", userToUpdate);
        model.addAttribute("message", message);
        model.addAttribute("emailError", email);
        return "profile";
    }

    @PutMapping("/profile")
    public String update(
        @Valid User user,
        BindingResult bindingResult,
        RedirectAttributes redirectAttributes,
        Authentication authentication 
    ) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addAttribute("message", "invalide");
            redirectAttributes.addFlashAttribute("userToUpdate", user);
        } else {
            User userToUpdate = (User) authentication.getPrincipal();
            if (userRepository.findByEmail(user.getEmail()) != null && !user.getEmail().equals(userToUpdate.getEmail())) {
                redirectAttributes.addAttribute("message", "invalide");
                redirectAttributes.addAttribute("emailError", "Email déjà utilisé");
                return "redirect:/profile";
            }
            userToUpdate.setFirstName(user.getFirstName());
            userToUpdate.setLastName(user.getLastName());
            userToUpdate.setEmail(user.getEmail());
            userToUpdate.setPhoneNumber(user.getPhoneNumber());
            userToUpdate.setAddress(user.getAddress());
            userToUpdate.setCity(user.getCity());
            userToUpdate.setPostalCode(user.getPostalCode());
            userToUpdate = userRepository.save(userToUpdate);
            redirectAttributes.addAttribute("message", "edit");
        }
        return "redirect:/profile";
    }

    @GetMapping("/password")
    public String password(Model model,@RequestParam(value = "message", required = false) String message
    ){
        model.addAttribute("message", message);
        return "password";
    }

    @PutMapping("/password")
    public String updatePass(
        @RequestParam("currentPassword") String currentPassword,
        @RequestParam("newPassword")String newPassword,
        @RequestParam("repeatPassword") String repeatPassword,
        RedirectAttributes redirectAttributes,
        Authentication authentication
    ) {

        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        User userToUpdate = (User) authentication.getPrincipal();

        if( !encoder.matches(currentPassword, userToUpdate.getPassword()) ) {
            redirectAttributes.addAttribute("message", "erreur");
            return"redirect:/password";
        }       
        if (encoder.matches(newPassword, encoder.encode(repeatPassword))) {  
            redirectAttributes.addAttribute("message", "edit");
            return "redirect:/profile";
        }
        redirectAttributes.addAttribute("message", "erreur");
        return "redirect:/password";
    }
}
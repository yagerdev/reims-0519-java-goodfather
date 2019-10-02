package fr.wildcodeschool.goodFather.controllers;

import fr.wildcodeschool.goodFather.entities.User;
import fr.wildcodeschool.goodFather.repositories.UserRepository;

import java.util.Collections;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserController implements WebMvcConfigurer{
    
    @Autowired
    UserRepository userRepository;

    
    @GetMapping("/users")
    public String show(Model model, @RequestParam(value = "message", required = false) String message, @Valid User user, BindingResult bindingResult) {
        if(user.getEmail() == null) {
            model.addAttribute("user", new User());
        } else { 
            model.addAttribute("user", user);
        }
        List<User> userList = userRepository.findAll();
        Collections.sort(userList);
        model.addAttribute("users", userList);
        model.addAttribute("message", message);

        return "admin/user";
    }

    @GetMapping("/users/{id}")
    public String edit(@PathVariable Long id, Model model) {
        User userToUpdate = userRepository.findById(id).get();
        model.addAttribute("user", userToUpdate);
        return "admin/user-edit";
    }

    @PostMapping("/users")
    public String create (
        RedirectAttributes redirectAttributes,
        @Valid User user,
        BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addAttribute("message", "invalide");
            redirectAttributes.addFlashAttribute("user", user);
        } else {
            if (userRepository.findByEmail(user.getEmail()) != null) {
                redirectAttributes.addAttribute("message", "email");
                return "redirect:/users";
            }
            userRepository.save(user);     
            redirectAttributes.addAttribute("message", "success");
        }
        return "redirect:/users";
    }

    @PutMapping("/users/{id}")
    public String update(@PathVariable Long id, User user, RedirectAttributes redirectAttributes) {
        User userToUpdate = userRepository.findById(id).get();
        userToUpdate.setFirstName(user.getFirstName());
        userToUpdate.setLastName(user.getLastName());
        userToUpdate.setEmail(user.getEmail());
        userToUpdate.setPhoneNumber(user.getPhoneNumber());
        userToUpdate.setAddress(user.getAddress());
        userToUpdate.setCity(user.getCity());
        userToUpdate.setPostalCode(user.getPostalCode());
        userToUpdate.setRole(user.getRole());
        userRepository.save(userToUpdate);
        redirectAttributes.addAttribute("message", "edit");
        redirectAttributes.addAttribute("user", userToUpdate);
        return "redirect:/users";
    }

    @DeleteMapping("/users/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        userRepository.deleteById(id);
        redirectAttributes.addAttribute("message", "delete");
        return "redirect:/users";
    }
}


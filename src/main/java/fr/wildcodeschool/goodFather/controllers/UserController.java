package fr.wildcodeschool.goodFather.controllers;

import fr.wildcodeschool.goodFather.entities.User;
import fr.wildcodeschool.goodFather.repositories.UserRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {
    
    @Autowired
    UserRepository userRepository;
    
    @GetMapping("/users")
    public String showAllUser(Model model){
        List<User> userList = userRepository.findAll();
        model.addAttribute("users", userList);
        return"admin/user";
    }

    @PostMapping("/users")
    public String createUser(
        @RequestParam("firstName") String firstName,
        @RequestParam("lastName") String lastName,
        @RequestParam("email") String email,
        @RequestParam("phoneNumber")String phoneNumber,
        @RequestParam("address") String address,
        @RequestParam("city") String city,
        @RequestParam("postalCode") String postalCode,
        @RequestParam("password") String password,
        @RequestParam("role") String role
        ) { 
            PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
            User user = new User(firstName, lastName, email, phoneNumber, address, city, postalCode, encoder.encode(password), role);
            userRepository.save(user);     
            return "redirect:/users";
    }

    @GetMapping("/403")
    public String forbiddenRedirect() {
        return "redirect:/home";
    }
}
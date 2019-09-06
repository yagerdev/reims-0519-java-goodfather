package fr.wildcodeschool.goodFather.controllers;

import fr.wildcodeschool.goodFather.entities.User;
import fr.wildcodeschool.goodFather.repositories.UserRepository;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {
    
    UserRepository userRepository;
    
    @GetMapping("/users")
    public String showAllUser(Model model){
        List<User> userList = userRepository.findAll();
        model.addAttribute("users", userList);
        return"admin-user";
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
            User user = new User(firstName,lastName,email,phoneNumber, address,city,postalCode,password,role);
            userRepository.save(user);     
        return "redirect:/users";
    }

    @DeleteMapping("/users/{id}")
    public String deleteUser(@PathVariable Long id){
        userRepository.deleteById(id);
        return "redirect:/users";
    }

}
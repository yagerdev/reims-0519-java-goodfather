package fr.wildcodeschool.goodFather.controllers;

import fr.wildcodeschool.goodFather.entities.User;
import fr.wildcodeschool.goodFather.repositories.UserRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {
    
    @Autowired
    UserRepository userRepository;
    
    @GetMapping("/users")
    public String show(Model model){
        List<User> userList = userRepository.findAll();
        model.addAttribute("users", userList);
        return "admin/user";
    }

    @PostMapping("/users")
    public String create(
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

    @PutMapping("/users/{id}")
    public String update(@PathVariable Long id, User user) {
        User userToUpdate = userRepository.findById(id).get();
        userToUpdate.setFirstname(user.getFirstName());
        userToUpdate.setLastname(user.getLastName());
        userToUpdate.setEmail(user.getEmail());
        userToUpdate.setPhoneNumber(user.getPhoneNumber());
        userToUpdate.setPhoneNumber(user.getPhoneNumber());
        userToUpdate.setAddress(user.getAddress());
        userToUpdate.setCity(user.getCity());
        userToUpdate.setPostalCode(user.getPostalCode());
        userToUpdate.setPassword(user.getPassword());
        userToUpdate.setRole(user.getRole());
        userRepository.save(userToUpdate);
        return "redirect:/users";
    }

    @DeleteMapping("/users/{id}")
    public String delete(@PathVariable Long id){
        userRepository.deleteById(id);
        return "redirect:/users";
    }

}
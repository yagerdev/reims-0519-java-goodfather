package fr.wildcodeschool.goodFather.controllers;

import fr.wildcodeschool.goodFather.entities.Project;
import fr.wildcodeschool.goodFather.entities.Quantity;
import fr.wildcodeschool.goodFather.entities.Room;
import fr.wildcodeschool.goodFather.entities.Task;
import fr.wildcodeschool.goodFather.entities.User;
import fr.wildcodeschool.goodFather.repositories.ProjectRepository;
import fr.wildcodeschool.goodFather.repositories.QuantityRepository;
import fr.wildcodeschool.goodFather.repositories.RoomRepository;
import fr.wildcodeschool.goodFather.repositories.TaskRepository;
import fr.wildcodeschool.goodFather.repositories.UserRepository;

import java.util.Collections;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserController {
    
    @Autowired
    UserRepository userRepository;

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    QuantityRepository quantityRepository;

    @Autowired
    RoomRepository roomRepository;
    
    @GetMapping("/users")
    public String show(Model model, @RequestParam(value = "message", required = false) String message){
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
    public String create(
        @RequestParam("firstName") String firstName,
        @RequestParam("lastName") String lastName,
        @RequestParam("email") String email,
        @RequestParam("phoneNumber")String phoneNumber,
        @RequestParam("address") String address,
        @RequestParam("city") String city,
        @RequestParam("postalCode") String postalCode,
        @RequestParam("password") String password,
        @RequestParam("role") String role,
        RedirectAttributes redirectAttributes
        ) { 
            PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
            User user = new User(
                firstName, 
                lastName, 
                email, 
                phoneNumber, 
                address, 
                city, 
                postalCode, 
                encoder.encode(password), 
                role
            );
            user = userRepository.save(user);
            if (user.getRole().equals("PARTNER")) {
                Task copyTask;
                for (Task task : taskRepository.findTasksByUserId(null)) {
                    copyTask = new Task(
                        task.getPrice(), 
                        task.getUnit(), 
                        task.getPercentRange(), 
                        task.getTypology(), 
                        task.getMaterial(), 
                        task.getWork(), 
                        user.getId()
                    );
                    copyTask = taskRepository.save(copyTask);
                }
            }
            redirectAttributes.addAttribute("message", "success");
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
        updatePartnerStatus(userToUpdate, userToUpdate.getRole(), user.getRole());
        userToUpdate.setRole(user.getRole());
        userRepository.save(userToUpdate);
        redirectAttributes.addAttribute("message", "edit");
        redirectAttributes.addAttribute("user", userToUpdate);
        return "redirect:/users";
    }

    @DeleteMapping("/users/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        for (Task task : taskRepository.findTasksByUserId(id)) {
            taskRepository.delete(task);
        }
        userRepository.deleteById(id);
        redirectAttributes.addAttribute("message", "delete");
        return "redirect:/users";
    }

    public void updatePartnerStatus(User userToUpdate, String from, String to) {
        if (from.equals("PARTNER") && !to.equals("PARTNER")){
            for (Project project : userToUpdate.getProjects()) {
                for (Room room : project.getRooms()) {
                    for (Quantity quantity : room.getQuantities()) {
                        Task task = quantity.getTask();
                        Task defaultTask = taskRepository.findTaskByWorkIdAndMaterialIdAndTypologyIdAndUserId(task.getWork().getId(), task.getMaterial().getId(), task.getTypology().getId(), null);
                        task.update(defaultTask.getPrice(), defaultTask.getPercentRange(), defaultTask.getUnit());
                        quantity.setTask(defaultTask);
                        quantityRepository.save(quantity);
                        task.getQuantities().remove(quantity);
                        taskRepository.save(task);
                    }
                    roomRepository.save(room);
                }
                project.setSourceId(null);
                projectRepository.save(project);
            }
            for (Task task : taskRepository.findTasksByUserId(userToUpdate.getId())) {
                taskRepository.delete(task);
            }
        } else if (!from.equals("PARTNER") && to.equals("PARTNER")) {
            Task copyTask;
            for (Task task : taskRepository.findTasksByUserId(null)) {
                copyTask = new Task(
                    task.getPrice(), 
                    task.getUnit(), 
                    task.getPercentRange(), 
                    task.getTypology(), 
                    task.getMaterial(), 
                    task.getWork(), 
                    userToUpdate.getId()
                );
                copyTask = taskRepository.save(copyTask);
            }
        }
    }
}
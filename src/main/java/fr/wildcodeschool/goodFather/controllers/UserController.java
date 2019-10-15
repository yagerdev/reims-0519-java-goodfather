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

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    QuantityRepository quantityRepository;

    @Autowired
    RoomRepository roomRepository;
    
    @GetMapping("/users")
    public String show(
        Model model, 
        @RequestParam(required = false) String message,
        @Valid User user, 
        BindingResult bindingResult
    ) {
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
    public String edit(
        @PathVariable Long id, 
        Model model,
        @RequestParam(required = false) String message
    ) {
        User userToUpdate = userRepository.findById(id).get();
        model.addAttribute("message", message);
        model.addAttribute("userToUpdate", userToUpdate);
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
        }
        return "redirect:/users";
    }

    @PutMapping("/users/{id}")
    public String update(
        Model model,
        @PathVariable Long id, 
        @Valid User user, 
        BindingResult bindingResult, 
        RedirectAttributes redirectAttributes
    ) {
        User userToUpdate = userRepository.findById(id).get();
        Boolean mailAlreadyUsed = userRepository.findByEmail(user.getEmail()) != null && !user.getEmail().equals(userToUpdate.getEmail());
        if (!mailAlreadyUsed && !bindingResult.hasErrors()) {
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
            return "redirect:/users";
        }
        if (mailAlreadyUsed) {
            model.addAttribute("message", "email");
            model.addAttribute("emailError", "Email déjà utilisé");
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("user", user);
        }
        model.addAttribute("message", "invalide");
        model.addAttribute("userToUpdate", userToUpdate);
        return "admin/user-edit";
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
                        Task defaultTask = taskRepository.findTaskByWorkIdAndMaterialIdAndTypologyIdAndUserId(
                            task.getWork().getId(),
                            task.getMaterial().getId(),
                            task.getTypology().getId(),
                            null
                        );
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

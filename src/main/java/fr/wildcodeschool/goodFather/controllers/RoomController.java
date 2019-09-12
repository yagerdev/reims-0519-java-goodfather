package fr.wildcodeschool.goodFather.controllers;

import fr.wildcodeschool.goodFather.entities.Category;
import fr.wildcodeschool.goodFather.entities.Material;
import fr.wildcodeschool.goodFather.entities.Project;
import fr.wildcodeschool.goodFather.entities.Room;
import fr.wildcodeschool.goodFather.entities.Typology;
import fr.wildcodeschool.goodFather.entities.Work;
import fr.wildcodeschool.goodFather.repositories.RoomRepository;
import fr.wildcodeschool.goodFather.repositories.TaskRepository;
import fr.wildcodeschool.goodFather.repositories.TypologyRepository;
import fr.wildcodeschool.goodFather.repositories.WorkRepository;
import fr.wildcodeschool.goodFather.repositories.CategoryRepository;
import fr.wildcodeschool.goodFather.repositories.MaterialRepository;
import fr.wildcodeschool.goodFather.repositories.ProjectRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RoomController {
    
    @Autowired
    RoomRepository roomRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    TypologyRepository typologyRepository;

    @Autowired
    WorkRepository workRepository;

    @Autowired
    MaterialRepository materialRepository;
    
    @GetMapping("/rooms/create")
    public String showCreateRoom(
        Model model,
        @RequestParam Long projectId,
        @RequestParam Long categoryId
    ){
        if (projectId == null) {
            return "redirect:/projects";
        }
        else if (categoryId == null) {
            return "redirect:/projects/" + projectId + "/edit";
        }
        model.addAttribute("projectId", projectId);
        model.addAttribute("categoryId", categoryId);
        return"room-create";
    }

    @PostMapping("/rooms")
    public String create(
        @RequestParam("walla") Double wallA,
        @RequestParam("wallb") Double wallB,
        @RequestParam("height") Double height,
        @RequestParam("project")Long projectId,
        @RequestParam("category") Long categoryId
        ) {
            Project project = projectRepository.getOne(projectId);
            Category category = categoryRepository.getOne(categoryId);
            Room room = new Room(wallA, wallB, height, category, project);
            room = roomRepository.save(room);
            Long id = room.getId();
            return "redirect:/rooms/"+id+"/edit";
    }

    @GetMapping("/rooms/{id}/edit")
    public String edit(@PathVariable("id") Long id, Model model){
        List<Typology> typologyList = typologyRepository.findAll();
        List<Work> workList = workRepository.findAll();
        List<Material> materialList = materialRepository.findAll();
        Room currentRoom = roomRepository.getOne(id);
        model.addAttribute("room", currentRoom);
        model.addAttribute("materials", materialList);
        model.addAttribute("typologies", typologyList);
        model.addAttribute("works", workList);
        return "tasks";
    }
   
}
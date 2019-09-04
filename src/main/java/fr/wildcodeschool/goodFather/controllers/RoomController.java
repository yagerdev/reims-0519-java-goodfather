package fr.wildcodeschool.goodFather.controllers;

import fr.wildcodeschool.goodFather.entities.Category;
import fr.wildcodeschool.goodFather.entities.Project;
import fr.wildcodeschool.goodFather.entities.Room;
import fr.wildcodeschool.goodFather.repositories.RoomRepository;
import fr.wildcodeschool.goodFather.repositories.CategoryRepository;
import fr.wildcodeschool.goodFather.repositories.ProjectRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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
    
    @GetMapping("/rooms/create")
    public String showCreateRoomForm(){
        return"room-create";
    }

    @PostMapping("/rooms")
    public String createRoom(
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

}
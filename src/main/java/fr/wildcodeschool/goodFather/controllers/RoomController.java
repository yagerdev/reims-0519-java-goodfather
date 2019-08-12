package fr.wildcodeschool.goodFather.controllers;

import fr.wildcodeschool.goodFather.entities.Room;
import fr.wildcodeschool.goodFather.repositories.RoomRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RoomController {
    
    @Autowired
    RoomRepository roomRepository;
    
    @GetMapping("/rooms/create")
    public String showCreateRoomForm(){

        return"room-create";
    }
    @PostMapping("/rooms/create")
    public String createRoom(@RequestParam("walla") Double wallA, @RequestParam("wallb") Double wallB, @RequestParam("height") Double height, @RequestParam("project")Long projectId, @RequestParam("category") Long categoryId) {
        Room room = new Room(wallA, wallB, height, categoryId);
        roomRepository.save(room);
        Long id = room.getId();
        return "redirect:/rooms/"+id+"/edit";
    }

    @GetMapping("/rooms/{id}/edit")
    public String showWorksForm() {
        return "works";
    }
}
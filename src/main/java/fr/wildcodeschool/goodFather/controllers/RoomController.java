package fr.wildcodeschool.goodFather.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.wildcodeschool.goodFather.entities.Material;
import fr.wildcodeschool.goodFather.entities.Project;
import fr.wildcodeschool.goodFather.entities.Room;
import fr.wildcodeschool.goodFather.entities.Typology;
import fr.wildcodeschool.goodFather.entities.Work;
import fr.wildcodeschool.goodFather.repositories.MaterialRepository;
import fr.wildcodeschool.goodFather.repositories.ProjectRepository;
import fr.wildcodeschool.goodFather.repositories.RoomRepository;
import fr.wildcodeschool.goodFather.repositories.TypologyRepository;
import fr.wildcodeschool.goodFather.repositories.WorkRepository;

@Controller
public class RoomController {
    
    @Autowired
    RoomRepository roomRepository;

    @Autowired
    TypologyRepository typologyRepository;

    @Autowired
    WorkRepository workRepository;

    @Autowired
    MaterialRepository materialRepository;

    @Autowired
    ProjectRepository projectRepository;
    
    @GetMapping("/rooms/create")
    public String showCreateRoom(
        Model model,
        @RequestParam Long projectId,
        @RequestParam Long categoryId
    ) {
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
    public String create(@ModelAttribute Room room) {
        room = roomRepository.save(room);
        return "redirect:/rooms/" + room.getId() + "/edit";
    }

    @GetMapping("/rooms/{id}/edit")
    public String edit(@PathVariable("id") Long id, Model model){
        List<Typology> typologyList = typologyRepository.findAll();
        List<Work> workList = workRepository.findAll();
        List<Material> materialList = materialRepository.findAll();
        Room currentRoom = roomRepository.getOne(id);
        model.addAttribute("idCurrentRoom", currentRoom);
        model.addAttribute("materials", materialList);
        model.addAttribute("typologies", typologyList);
        model.addAttribute("works", workList);
        return "tasks";
    }

    @DeleteMapping("/rooms/{id}")
    public String delete(@PathVariable Long id, Model model) {
        Room room = roomRepository.findById(id).get();
        Long projectId = room.getProject().getId();
        roomRepository.deleteById(id);
        return "redirect:/projects/"+projectId+"/read";
    }
}
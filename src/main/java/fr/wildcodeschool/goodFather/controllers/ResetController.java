package fr.wildcodeschool.goodFather.controllers;

import org.springframework.web.bind.annotation.GetMapping;

public class ResetController{


    @GetMapping("/reset")
    public String edit() {
      
        return "admin/user-edit";
    }

}
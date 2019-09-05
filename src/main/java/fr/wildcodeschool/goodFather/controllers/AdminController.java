package fr.wildcodeschool.goodFather.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @GetMapping("/admin")
    public String getHomeAdmin() {
        return "admin/home";
    }

}
package fr.wildcodeschool.goodFather.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/login")
    public String login() {
        return "redirect:/";
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

}
package fr.wildcodeschool.goodFather.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class IndexController {

    @GetMapping("/")
    public String index(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object isLogged = authentication.getPrincipal();
        Boolean bool = isLogged.getClass() == fr.wildcodeschool.goodFather.entities.User.class;
        model.addAttribute("isLogged", bool);
        System.out.println(isLogged.getClass().toString());
        return "index";
    }

}
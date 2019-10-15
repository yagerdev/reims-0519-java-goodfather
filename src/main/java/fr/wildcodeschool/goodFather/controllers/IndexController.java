package fr.wildcodeschool.goodFather.controllers;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {

    @GetMapping("/")
    public String index(Model model, @RequestParam(value = "error", required = false) Boolean error) {
        if (error != null) {
            if (error) {
                model.addAttribute("message", "passwordError");
                return "index";
            }
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth instanceof AnonymousAuthenticationToken)
            return "index";
        else {
            return "redirect:/home";
        }
    }

}

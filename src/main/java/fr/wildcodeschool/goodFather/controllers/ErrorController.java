package fr.wildcodeschool.goodFather.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorController {

    @GetMapping("/error")
    public String error(HttpServletRequest httpRequest) {
        return "error";
    }

}
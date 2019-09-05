package fr.wildcodeschool.goodFather.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FragmentsController {

    @GetMapping("/footer")
    public String getFooter() {
        return "footer";
    }

    @GetMapping("/script")
    public String getScript() {
        return "script";
    }

    @GetMapping("/head")
    public String getHead() {
        return "head";
    }

    @GetMapping("/header")
    public String getHeader() {
        return "header";
    }

}
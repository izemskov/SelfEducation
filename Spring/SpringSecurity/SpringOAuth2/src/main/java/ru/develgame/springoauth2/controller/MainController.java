package ru.develgame.springoauth2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class MainController {
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/secure/{page}")
    public String secure(@PathVariable String page) {
        return "secure/" + page;
    }

    @GetMapping("/callback")
    public String callback() {
        return "callback";
    }
}

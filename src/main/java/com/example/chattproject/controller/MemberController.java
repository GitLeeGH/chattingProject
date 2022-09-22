package com.example.chattproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MemberController {

    @RequestMapping("/login")
    public String login() {

        return "/login";
    }

    @RequestMapping("/register")
    public String register() {

        return "/register";
    }

}

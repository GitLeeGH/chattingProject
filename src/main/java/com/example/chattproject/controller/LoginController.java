package com.example.chattproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/chatlogin")
    public String goLogin(){
        System.out.println("login");

        return "/chatlogin";
    }
}

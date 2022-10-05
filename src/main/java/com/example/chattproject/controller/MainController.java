package com.example.chattproject.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String index(){

        return "layout/basic";


    }

    @GetMapping("/mentor")
    public String mentor(){

        return "mentoring/myMentoring";
    }

    @GetMapping("/roomRoom")
    public String room(){

        return "room";
    }


}

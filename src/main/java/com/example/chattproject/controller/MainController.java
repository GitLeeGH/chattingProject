package com.example.example.controller;


import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Log4j2
public class MainController {

    @RequestMapping("/")
    public String index(){

        return "index";


    }
}

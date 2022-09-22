package com.example.chattproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ChatController {

    @RequestMapping("/chatting")
    public String chatting(){
        return "chatting";
    }

}

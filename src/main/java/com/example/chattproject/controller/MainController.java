package com.example.chattproject.controller;


import com.example.chattproject.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final ChatService cs;

    @GetMapping("/")
    public String index(){

        return "layout/basic";


    }

    @GetMapping("/mentoring/myMentoring")
    public String mentor(Model model, Principal principal){

        String seller = principal.getName();
        // 채팅창 목록 불러오기
        model.addAttribute("rooms1",cs.findRoomByBuyer(seller));
        model.addAttribute("rooms",cs.findRoomBySeller(seller));

        System.out.println(model);

        return "mentoring/myMentoring";
    }

    @GetMapping("/roomRoom")
    public String room(){

        return "room";
    }


}

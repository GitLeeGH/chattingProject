package com.example.chattproject.controller;

import com.example.chattproject.exception.BadRequestException;
import com.example.chattproject.repository.UserRepository;
import com.example.chattproject.domain.entity.User;
import com.example.chattproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/account")
public class AccountController {
//    @GetMapping("/")
//    public String main() {
//        return "index";
//    }


    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login() {
        return "account/login";
    }

    @GetMapping("/register")
    public String register() {
        return "account/register";
    }

    @PostMapping("/register")
    public String register(User user) {
        userService.save(user);
        return "redirect:/";
    }

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/admin")
    public String admin(Model model) {
        List<User> users = userRepository.findAll();
//        User users1 = userRepository.findById("1@naver.com");
//        Integer a = users1.getNumber();
//        users1.setNumber(a+1);
//        userService.save(users1);
        model.addAttribute("users", users);
        return "account/admin";
    }

    // 아이디 중복 체크
    @GetMapping("/email/check")
    public ResponseEntity<?> checkEmailDuplication(@RequestParam(value="email")String email) throws BadRequestException {
        System.out.println(email);
        if(userService.existsByEmail(email)==true){
            throw new BadRequestException("이미 사용중인 아이디 입니다.");
        }else{
            return ResponseEntity.ok("사용 가능한 아이디 입니다.");
        }
    }

}
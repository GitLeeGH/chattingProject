package com.example.chattproject.controller;

import com.example.chattproject.domain.entity.User;
import com.example.chattproject.repository.UserRepository;
import com.example.chattproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.validation.*;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

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


    @Target({TYPE, FIELD, ANNOTATION_TYPE})
    @Retention(RUNTIME)
    @Constraint(validatedBy = EmailValidator.class)
    @Documented
    public @interface ValidEmail {
        String message() default "Invalid email";
        Class<?>[] groups() default {};
        Class<? extends Payload>[] payload() default {};
    }



    public class EmailValidator
            implements ConstraintValidator<ValidEmail, String> {

        private Pattern pattern;
        private Matcher matcher;
        private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-+]+(.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(.[A-Za-z0-9]+)*(.[A-Za-z]{2,})$";
        @Override
        public void initialize(ValidEmail constraintAnnotation) {
        }
        @Override
        public boolean isValid(String email, ConstraintValidatorContext context){
            return (validateEmail(email));
        }
        private boolean validateEmail(String email) {
            pattern = Pattern.compile(EMAIL_PATTERN);
            matcher = pattern.matcher(email);
            return matcher.matches();
        }
    }

    @Target({TYPE,ANNOTATION_TYPE})
    @Retention(RUNTIME)
    @Constraint(validatedBy = PasswordMatchesValidator.class)
    @Documented
    public @interface PasswordMatches {
        String message() default "Passwords don't match";
        Class<?>[] groups() default {};
        Class<? extends Payload>[] payload() default {};
    }

    public class PasswordMatchesValidator
            implements ConstraintValidator<PasswordMatches, Object> {

        @Override
        public void initialize(PasswordMatches constraintAnnotation) {
        }
        @Override
        public boolean isValid(Object obj, ConstraintValidatorContext context){
//            User user = (User) obj;
            return true/*user.getPassword().equals(user.getMatchingPassword())*/;
        }
    }

    @RequestMapping(value = "/email/check", method = RequestMethod.POST)
    @ResponseBody
    public Boolean checkEmailDuplication(@RequestParam(value="email")String email) /*throws BadRequestException*/ {
        return userService.existsByEmail(email);
    }



}
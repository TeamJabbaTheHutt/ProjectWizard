package com.hauxy.projectwizard.controller;

import com.hauxy.projectwizard.model.User;
import com.hauxy.projectwizard.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String showRegisterForm() {
        return "register";
    }


    @PostMapping("/register")
    public String registerUser(@RequestParam("username") String username, @RequestParam("email") String email, @RequestParam("password") String password, Model model, HttpSession httpSession) {

        if (!userService.checkIfUserExist(email)) {
            model.addAttribute("username", username);
            model.addAttribute("email",email);
            model.addAttribute("password",password);
            User user = new User(username,email, password);

            if (userService.createNewUser(user).equals("Success")) {
                user = userService.getUserByEmail(email);
                httpSession.setAttribute("loggedInUser", user);
                return "redirect:/project/home";
            } else {
                model.addAttribute("message", "Failed to create user, try again");
                model.addAttribute("messageType", "error");
                return "register";
            }

        } else {
            model.addAttribute("message", "A user with this email already exist");
            model.addAttribute("messageType", "error");
            return "register";
        }

    }
}

package com.hauxy.projectwizard.controller;

import com.hauxy.projectwizard.model.User;
import com.hauxy.projectwizard.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
}

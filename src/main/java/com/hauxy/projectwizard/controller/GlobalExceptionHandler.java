package com.hauxy.projectwizard.controller;


import com.hauxy.projectwizard.exceptions.UserNotLoggedInException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(UserNotLoggedInException.class)
    public String handleUserNotLoggedIn(UserNotLoggedInException ex, Model model) {
        model.addAttribute("message", "Please log in to access this page.");
        return "login";
    }
}

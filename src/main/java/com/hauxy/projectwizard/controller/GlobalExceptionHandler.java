package com.hauxy.projectwizard.controller;


import com.hauxy.projectwizard.exceptions.DatabaseOperationException;
import com.hauxy.projectwizard.exceptions.UserNotLoggedInException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(UserNotLoggedInException.class)
    public String handleUserNotLoggedIn(UserNotLoggedInException ex, Model model) {
        model.addAttribute("message", ex.getMessage());
        return "login";
    }

    @ExceptionHandler({ DatabaseOperationException.class})
    public String handleGeneric(Exception ex, Model model) {
        model.addAttribute("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        model.addAttribute("error", "Internal Server Error");
        model.addAttribute("message", ex.getMessage());
        return "error/500";
    }
}

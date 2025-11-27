package com.hauxy.projectwizard.controller;

import com.hauxy.projectwizard.exceptions.UserNotLoggedInException;
import com.hauxy.projectwizard.model.Project;
import com.hauxy.projectwizard.model.User;
import com.hauxy.projectwizard.service.ProjectService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
@RequestMapping("/project")
public class ProjectController {
    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/createProject")
    public String createProject(@RequestParam("title") String title, @RequestParam("description") String description, @RequestParam("deadline") LocalDate deadline, Model model, HttpSession httpSession) {
        User user = (User) httpSession.getAttribute("loggedInUser");

        if (user == null) {
            return "redirect:/login";
        }

        Project project = new Project(title, description, deadline);
        String result = projectService.createNewProject(project);

        if (result.equals("Success")) {

        }

        return "createProject";
    }





    @GetMapping("/home")
    public String home(Model model, HttpSession httpSession) {

        try {
            User user = (User) httpSession.getAttribute("loggedInUser");
            model.addAttribute("UsersListOfProjects", projectService.getUsersProjectsByUserId(user.getUserId()));
            return "homepage";
        } catch (NullPointerException e) {
            throw new UserNotLoggedInException("you might not be logged in", e);

        }

    }
}

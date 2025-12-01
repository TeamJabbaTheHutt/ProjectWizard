package com.hauxy.projectwizard.controller;

import com.hauxy.projectwizard.exceptions.UserNotLoggedInException;
import com.hauxy.projectwizard.model.Project;
import com.hauxy.projectwizard.model.User;
import com.hauxy.projectwizard.service.ProjectService;
import com.hauxy.projectwizard.service.StatisticsService;
import jakarta.servlet.http.HttpSession;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
@RequestMapping("/project")
public class ProjectController {
    private final ProjectService projectService;
    private final StatisticsService statisticsService;

    public ProjectController(ProjectService projectService, StatisticsService statisticsService) {
        this.projectService = projectService;
        this.statisticsService = statisticsService;
    }

    @GetMapping("/createProject")
    public String showCreateProjectForm(Model model, HttpSession httpSession) {
        User user = (User) httpSession.getAttribute("loggedInUser");

        if (user == null) {
            return "redirect:/login";
        }

        LocalDate today = LocalDate.now();
        model.addAttribute("todaysDate", today);
        return "createProject";
    }

    @PostMapping("/createProject")
    public String createProject(@RequestParam String title,
                                @RequestParam String description,
                                @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate deadline,
                                Model model,
                                HttpSession session) {

        User user = (User) session.getAttribute("loggedInUser");

        if (user == null) {
            return "redirect:/login";
        }

        Project project = new Project(title, description, deadline);

        try {
            projectService.createProjectWithCreator(project, user.getUserId());
            model.addAttribute("message", "Project created successfully!");

            return "redirect:/project/home";

        } catch (RuntimeException e) {
            e.printStackTrace();
            model.addAttribute("error", "Could not create project. Please try again.");
            return "createProject";
        }
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

package com.hauxy.projectwizard.controller;

import com.hauxy.projectwizard.exceptions.UserNotLoggedInException;
import com.hauxy.projectwizard.model.Project;
import com.hauxy.projectwizard.model.User;
import com.hauxy.projectwizard.repository.DAO.ProjectDAO;
import com.hauxy.projectwizard.repository.DAO.UserDAO;
import com.hauxy.projectwizard.service.ProjectService;
import com.hauxy.projectwizard.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping("/project")
public class ProjectController {
    private final ProjectService projectService;
    private final UserService userService;


    public ProjectController(ProjectService projectService, UserService userService) {
        this.projectService = projectService;
        this.userService = userService;
    }

    @GetMapping("/{projectId}/edit")
    public String showEditProjectPage(@PathVariable int projectId, Model model, HttpSession httpSession) {

        User user = (User) httpSession.getAttribute("loggedInUser");

        if (user == null) {
            return "redirect:/login";
        }
        Project project = projectService.getProjectById(projectId);
        model.addAttribute("project", project);
        model.addAttribute("members", projectService.getProjectMembers(projectId));

        return "editProject";
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

    @PostMapping("/{projectId}/edit")
    public String updateProject(
            @PathVariable int projectId,
            @RequestParam String title,
            @RequestParam String description,
            @RequestParam String deadline,
            HttpSession session
    ) {
        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }

        projectService.updateProject(projectId, title, description, deadline);

        return "redirect:/project/dashboard";
    }

    @PostMapping("/{projectId}/remove-member")
    public String removeMember(
            @PathVariable int projectId,
            @RequestParam("memberId") int memberId,
            HttpSession session
    ) {
        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }

        projectService.removeMember(projectId, memberId);

        return "redirect:/project/" + projectId + "/edit";
    }

    @PostMapping("/{projectId}/add-member")
    public String addMember(
            @PathVariable int projectId,
            @RequestParam("newMemberEmail") String email,
            Model model,
            HttpSession session
    ) {
        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }

        User user = userService.getUserByEmail(email);

        if (user == null) {
            model.addAttribute("errorMessage", "User with that email does not exist!");
            model.addAttribute("project", projectService.getProjectById(projectId));
            model.addAttribute("members", projectService.getProjectMembers(projectId));
            return "editProject";
        }

        projectService.addMember(projectId, user.getUserId());

        return "redirect:/project/" + projectId + "/edit";
    }

}

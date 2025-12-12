package com.hauxy.projectwizard.controller;

import com.hauxy.projectwizard.exceptions.DatabaseOperationException;
import com.hauxy.projectwizard.exceptions.UserNotLoggedInException;
import com.hauxy.projectwizard.model.Project;
import com.hauxy.projectwizard.model.User;
import com.hauxy.projectwizard.service.*;
import com.hauxy.projectwizard.service.StatisticsService;
import jakarta.servlet.http.HttpSession;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.time.LocalDate;

@Controller
@RequestMapping("/project")
public class ProjectController {
    private final ProjectService projectService;
    private final UserService userService;
    private final LoginService loginService;
    private User user;
    private HttpSession session;


    public ProjectController(ProjectService projectService, UserService userService, LoginService loginService, HttpSession session) {
        this.projectService = projectService;
        this.userService = userService;
        this.loginService = loginService;
        this.session = session;
    }

    @GetMapping("/{projectId}/edit")
    public String showEditProjectPage(@PathVariable int projectId, Model model) {
        user = loginService.checkIfLoggedInAndGetUser(session);
        try {
            Project project = projectService.getProjectById(projectId);
            model.addAttribute("project", project);
            model.addAttribute("members", projectService.getProjectMembers(projectId));
        } catch (EmptyResultDataAccessException e) {
            throw new DatabaseOperationException("Project does not exist or cannot be found", e);
        }

        return "editProject";
    }

    @GetMapping("/createProject")
    public String showCreateProjectForm(Model model) {
        user = loginService.checkIfLoggedInAndGetUser(session);

        LocalDate today = LocalDate.now();
        model.addAttribute("todaysDate", today);
        return "createProject";
    }

    @PostMapping("/createProject")
    public String createProject(@RequestParam String title, @RequestParam String description, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate deadline, Model model) {
        user = loginService.checkIfLoggedInAndGetUser(session);


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
    public String home(Model model) {

        user = loginService.checkIfLoggedInAndGetUser(session);

        try {
            model.addAttribute("UsersListOfProjects", projectService.getUsersProjectsByUserId(user.getUserId()));

        } catch (EmptyResultDataAccessException e) {
            throw new DatabaseOperationException("Project does not exist or cannot be found", e);
        }
        return "homepage";

    }

    @PostMapping("/{projectId}/edit")
    public String updateProject(@PathVariable int projectId, @RequestParam String title, @RequestParam String description, @RequestParam String deadline) {

        projectService.updateProject(projectId, title, description, deadline);

        return "redirect:/project/dashboard/" + projectId;
    }

    @PostMapping("/{projectId}/remove-member")
    public String removeMember(@PathVariable int projectId, @RequestParam("removeMemberEmail") String email, Model model) {


        User userToRemove = userService.getUserByEmail(email);

        if (userToRemove == null) {
            model.addAttribute("errorMessage", "No user exists with that email!");
            model.addAttribute("project", projectService.getProjectById(projectId));
            model.addAttribute("members", projectService.getProjectMembers(projectId));
            return "editProject";
        }

        projectService.removeMember(projectId, user.getUserId());

        return "redirect:/project/" + projectId + "/edit";
    }

    @PostMapping("/{projectId}/add-member")
    public String addMember(@PathVariable int projectId, @RequestParam("newMemberEmail") String email, Model model) {



        User userToAdd = userService.getUserByEmail(email);

        if (userToAdd == null) {
            model.addAttribute("errorMessage", "User with that email does not exist!");
            model.addAttribute("project", projectService.getProjectById(projectId));
            model.addAttribute("members", projectService.getProjectMembers(projectId));
            return "editProject";
        }

        projectService.addUserToProject(user.getUserId(), projectId);

        return "redirect:/project/" + projectId + "/edit";
    }


    @PostMapping("/deleteProject")
    public String deleteProject(@RequestParam int projectId, RedirectAttributes redirectAttributes) {


        boolean success = projectService.deleteProject(projectService.getProjectById(projectId));


        if (!success) {
            redirectAttributes.addFlashAttribute("errorMessage", "Could not delete subproject.");
        }

        return "redirect:/project/home";
    }

    @GetMapping("dashboard/{projectId}")
    public String projectDashboard(@PathVariable int projectId, Model model) {
        user = loginService.checkIfLoggedInAndGetUser(session);


        try {
            model.addAttribute("project", projectService.getProjectById(projectId));
            model.addAttribute("subprojects", projectService.getAllSubProjectsByProjectId(projectId));
            model.addAttribute("members", projectService.getProjectMembers(projectId));

        } catch (EmptyResultDataAccessException e) {
            throw new DatabaseOperationException("Project does not exist or cannot be found", e);
        }
        return "projectDashboard";
    }
}

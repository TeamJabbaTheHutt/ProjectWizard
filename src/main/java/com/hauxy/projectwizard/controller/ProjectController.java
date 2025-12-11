package com.hauxy.projectwizard.controller;

import com.hauxy.projectwizard.exceptions.UserNotLoggedInException;
import com.hauxy.projectwizard.model.*;
import com.hauxy.projectwizard.repository.DAO.ProjectDAO;
import com.hauxy.projectwizard.repository.DAO.UserDAO;
import com.hauxy.projectwizard.service.*;

import com.hauxy.projectwizard.model.Project;
import com.hauxy.projectwizard.model.User;
import com.hauxy.projectwizard.service.*;
import com.hauxy.projectwizard.service.StatisticsService;
import jakarta.servlet.http.HttpSession;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/project")
public class ProjectController {
    private final ProjectService projectService;
    private final UserService userService;
    private final StatisticsService statisticsService;
    private final SubprojectService subprojectService;
    private final TaskService taskService;
    private final SubtaskService subtaskService;


    public ProjectController(ProjectService projectService, UserService userService, StatisticsService statisticsService, SubprojectService subprojectService, TaskService taskService, SubtaskService subtaskService) {
        this.projectService = projectService;
        this.userService = userService;
        this.statisticsService = statisticsService;
        this.subprojectService = subprojectService;
        this.taskService = taskService;
        this.subtaskService = subtaskService;
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
    public String createProject(@RequestParam String title, @RequestParam String description, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate deadline, Model model, HttpSession session) {
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

    @GetMapping("/dashboard/{projectId}")
    public String showProjectDashboard(@PathVariable int projectId, Model model, HttpSession httpSession) {

        // User user = (User) httpSession.getAttribute("loggedInUser");
        model.addAttribute("project", projectService.getProjectById(projectId));

        List<Subproject> subProjects = subprojectService.getAllSubProjectsByProjectId(projectId);

        for (Subproject sp : subProjects) {
            List<Task> tasks = taskService.getAllTasksBySubprojectId(sp.getSubProjectId());

            for (Task task : tasks) {
                List<Subtask> subtasks = subtaskService.getAllSubTasksByTaskId(task.getTaskId());
                if (subtasks == null) {
                    subtasks = new ArrayList<>();
                }
                task.setSubtasks(subtasks);
            }
            sp.setTasks(tasks);

        }


        model.addAttribute("subProjects", subprojectService.getAllSubProjectsByProjectId(projectId));
        return "projectDashboard";
    }

    @PostMapping("/{projectId}/edit")
    public String updateProject(@PathVariable int projectId, @RequestParam String title, @RequestParam String description, @RequestParam String deadline, HttpSession session) {
        if (session.getAttribute("loggedInUser") == null) {
            return "redirect:/login";
        }

        projectService.updateProject(projectId, title, description, deadline);

        return "redirect:/project/dashboard/" + projectId;
    }

    @PostMapping("/{projectId}/remove-member")
    public String removeMember(@PathVariable int projectId, @RequestParam("removeMemberEmail") String email, HttpSession session, Model model) {
        if (session.getAttribute("loggedInUser") == null) {
            return "redirect:/login";
        }

        User user = userService.getUserByEmail(email);

        if (user == null) {
            model.addAttribute("errorMessage", "No user exists with that email!");
            model.addAttribute("project", projectService.getProjectById(projectId));
            model.addAttribute("members", projectService.getProjectMembers(projectId));
            return "editProject";
        }

        projectService.removeMember(projectId, user.getUserId());

        return "redirect:/project/" + projectId + "/edit";
    }

    @PostMapping("/{projectId}/add-member")
    public String addMember(@PathVariable int projectId, @RequestParam("newMemberEmail") String email, Model model, HttpSession session) {

        if (session.getAttribute("loggedInUser") == null) {
            return "redirect:/login";
        }

        User user = userService.getUserByEmail(email);

        if (user == null) {
            model.addAttribute("errorMessage", "User with that email does not exist!");
            model.addAttribute("project", projectService.getProjectById(projectId));
            model.addAttribute("members", projectService.getProjectMembers(projectId));
            return "editProject";
        }

        projectService.addUserToProject(user.getUserId(), projectId);

        return "redirect:/project/" + projectId + "/edit";
    }


    @PostMapping("/deleteProject")
    public String deleteProject(@RequestParam int projectId, HttpSession session, RedirectAttributes redirectAttributes) {
        boolean success = projectService.deleteProject(projectService.getProjectById(projectId));

        if (!success) {
            redirectAttributes.addFlashAttribute("errorMessage", "Could not delete subproject.");
        }

        return "redirect:/project/home";
    }
}

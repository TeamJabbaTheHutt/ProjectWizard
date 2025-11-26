package com.hauxy.projectwizard.controller;

import com.hauxy.projectwizard.exceptions.UserNotLoggedInException;
import com.hauxy.projectwizard.model.Project;
import com.hauxy.projectwizard.model.User;
import com.hauxy.projectwizard.service.ProjectService;
import jakarta.servlet.http.HttpSession;
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

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/createProject")
    public String createProject(@RequestParam("title") String title, @RequestParam("description") String description, @RequestParam("deadline") LocalDate deadline, Model model, HttpSession httpSession) {

//        if ()
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

    /*@PostMapping("/project/addMember")
    public String addMember(@RequestParam("newMemberEmail") String email, @RequestParam("projectId") int projectId, Model model) {
        // 1. Tjek om brugeren eksisterer
        User user = UserDao.getUserByEmail(email);

        if (user == null) {
            // Send fejlbesked tilbage til HTML
            model.addAttribute("errorMessage", "User with that email does not exist!");

            // Genindlæs dine data så siden virker
            Project project = projectDao.getProjectById(projectId);
            model.addAttribute("project", project);

            return "edit-project"; // navnet på din html/jsp
        }

        // 2. Tilføj medlem hvis brugeren findes
        projectDao.addMemberToProject(projectId, user.getUserId());

        // 3. Redirect tilbage til projektet
        return "redirect:/project/edit?projectId=" + projectId;
    }
     */


    /* @PostMapping("/project/update")
        public String updateProject(Project project) {
            projectService.update(project);
            return "redirect:/project/dashboard";
        }
    */
}

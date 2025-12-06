package com.hauxy.projectwizard.controller;

import com.hauxy.projectwizard.model.Subtask;
import com.hauxy.projectwizard.model.Task;
import com.hauxy.projectwizard.model.User;
import com.hauxy.projectwizard.service.SubtaskService;
import com.hauxy.projectwizard.service.TaskService;
import org.springframework.format.annotation.DateTimeFormat;
import com.hauxy.projectwizard.model.*;
import com.hauxy.projectwizard.service.SubprojectService;
import com.hauxy.projectwizard.service.SubtaskService;
import com.hauxy.projectwizard.service.TaskService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/task")
public class TaskController {
    private final TaskService taskService;
    private final SubtaskService subtaskService;
    private final SubprojectService subprojectService;
    public TaskController(TaskService taskService, SubtaskService subtaskService,  SubprojectService subprojectService) {
        this.taskService = taskService;
        this.subtaskService = subtaskService;
        this.subprojectService = subprojectService;
    }

    @GetMapping("/createTask/{projectId}/{parentId}")
    public String showCreateTaskForm(
            @PathVariable int projectId,
            @PathVariable int parentId,
            Model model) {

        Task task = new Task();
        model.addAttribute("projectId", projectId);
        task.setParentId(parentId);
        model.addAttribute("task", task);


        return "createTask";
    }


    @PostMapping("/saveTask/{projectId}")
    public String saveTask(@ModelAttribute Task task, @PathVariable int projectId) {
        System.out.println(task.getParentId());
        taskService.createTask(
                task.getTitle(),
                task.getDescription(),
                task.getParentId(),
                task.getDeadline()
        );

        return "redirect:/projectDashboard/" + projectId;
    }

    @GetMapping("/createSubproject/{projectId}")
    public String createSubproject(@PathVariable int projectId, Model model) {
        Subproject subproject = new Subproject();
        subproject.setParentId(projectId);
        model.addAttribute("subproject", subproject);
        model.addAttribute("projectId", projectId);
        return "createSubProject";
    }

    @PostMapping("/saveSubproject/{projectId}")
    public String saveSubProject(@ModelAttribute Subproject subproject, @PathVariable int projectId) {
        subprojectService.createSubproject(subproject);
        return "redirect:/projectDashboard/" + projectId;
    }

    @GetMapping("/createSubtask/{projectId}/{parentId}")
    public String createSubtask(@PathVariable int projectId,@PathVariable int parentId, Model model) {
        Subtask subtask = new Subtask();
        subtask.setParentId(parentId);
        model.addAttribute("subtask", subtask);
        model.addAttribute("projectId", projectId);
        return "createSubtask";
    }
    @PostMapping("/saveSubtask/{projectId}")
    public String saveSubtask(@ModelAttribute Subtask subtask, @PathVariable int projectId) {
        subtaskService.createSubtask(subtask);
        return "redirect:/projectDashboard/" + projectId;
    }

    @GetMapping("/subproject/{subprojectId}/{projectId}/edit")
    public String showEditSubprojectPage(@PathVariable int projectId, @PathVariable int subprojectId, Model model, HttpSession httpSession) {

        User user = (User) httpSession.getAttribute("loggedInUser");

        if (user == null) {
            return "redirect:/login";
        }
        Subproject subproject = subprojectService.getSubprojectById(subprojectId, projectId);
        System.out.println(subproject.getDeadline());
        model.addAttribute("subproject", subproject);

        return "editSubproject";
    }

    @PostMapping("/subproject/{subProjectId}/{projectId}/save")
    public String updateSubproject(
            @PathVariable int subProjectId,
            @PathVariable int projectId,
            @RequestParam String title,
            @RequestParam String description,
            @RequestParam String deadline,
            HttpSession session
    ) {
        if (session.getAttribute("loggedInUser") == null) {
            return "redirect:/login";
        }
        subprojectService.updateSubproject(subProjectId, title, description, deadline);

        return "redirect:/project/dashboard/" + projectId;
    }


    @GetMapping("/editTask/{taskId}/{projectId}")
    public String editTask(@PathVariable int taskId, @PathVariable int projectId, Model model) {
        model.addAttribute("task", taskService.getTaskById(taskId));
        model.addAttribute("projectId", projectId);
        return "editTask";
    }

    @PostMapping("/editTask/addAssignee")
    public String editTaskAddAssignee(@RequestParam int taskId,
                                      @RequestParam int projectId,
                                      @RequestParam String userEmail,
                                      RedirectAttributes redirectAttributes) {



        boolean success = taskService.addUserToTask(userEmail, taskId);


        if (!success) {
            redirectAttributes.addFlashAttribute("errorMessage", "User not found or already assigned.");
        }

        return "redirect:/task/editTask/" + taskId + "/" + projectId;
    }

    @PostMapping("/editTask/removeAssignee")
    public String editTaskRemoveAssignee(@RequestParam int taskId,
                                      @RequestParam int projectId,
                                      RedirectAttributes redirectAttributes) {


        boolean success = taskService.removeUserFromTask(taskId);
        if (!success) {
            redirectAttributes.addFlashAttribute("errorMessage", "User not found or already assigned.");
        }
        return "redirect:/task/editTask/" + taskId + "/" + projectId;
    }

    @PostMapping("/editTask/save")
    public String editTaskSave(@ModelAttribute Task task,
                               @RequestParam int projectId,
                               RedirectAttributes redirectAttributes) {

        boolean success = taskService.updatetask(task);

        if (!success) {
            redirectAttributes.addFlashAttribute("errorMessage", "Could not update task.");
        }

        return "redirect:/project/dashboard/" + projectId;
    }


    // mgormvoemvopmreopvmromvpermvmormovre

    @GetMapping("/editSubtask/{subtaskId}/{projectId}")
    public String editSubtask(@PathVariable int subtaskId, @PathVariable int projectId, Model model) {
        model.addAttribute("subtask", subtaskService.getTaskById(subtaskId));
        model.addAttribute("projectId", projectId);
        return "editSubtask";
    }

    @PostMapping("/editSubtask/addAssignee")
    public String editSubtaskAddAssignee(@RequestParam int subtaskId,
                                      @RequestParam int projectId,
                                      @RequestParam String userEmail,
                                      RedirectAttributes redirectAttributes) {



        boolean success = subtaskService.addUserToSubtask(userEmail, subtaskId);


        if (!success) {
            redirectAttributes.addFlashAttribute("errorMessage", "User not found or already assigned.");
        }

        return "redirect:/task/editSubtask/" + subtaskId + "/" + projectId;
    }

    @PostMapping("/editSubtask/removeAssignee")
    public String editTSubaskRemoveAssignee(@RequestParam int subtaskId,
                                         @RequestParam int projectId,
                                         RedirectAttributes redirectAttributes) {


        boolean success = subtaskService.removeUserFromTask(subtaskId);
        if (!success) {
            redirectAttributes.addFlashAttribute("errorMessage", "User not found or already assigned.");
        }
        return "redirect:/task/editSubtask/" + subtaskId + "/" + projectId;
    }

    @PostMapping("/editSubtask/save")
    public String editSubtaskSave(@ModelAttribute Subtask subtask,
                               @RequestParam int projectId,
                               RedirectAttributes redirectAttributes) {

        boolean success = subtaskService.updateSubtask(subtask);

        if (!success) {
            redirectAttributes.addFlashAttribute("errorMessage", "Could not update task.");
        }

        return "redirect:/project/dashboard/" + projectId;
    }





}


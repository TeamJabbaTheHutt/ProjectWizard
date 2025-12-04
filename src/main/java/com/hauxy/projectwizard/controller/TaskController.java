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

//    @GetMapping("/create/{projectId}")
//    public String showCreateTaskForm(
//            @PathVariable int projectId,
//            @RequestParam(required = false) Integer parentTaskId,
//            Model model) {
//
//        model.addAttribute("task", new Task());
//        model.addAttribute("projectId", projectId);
//
//        if (parentTaskId != null) {
//            Task parentTask = taskService.getTaskById(parentTaskId);
//            model.addAttribute("parentTask", parentTask);
//        }
//
//        return "createTask";
//    }
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

    @GetMapping("/{taskId}/{subprojectId}/{projectId}/edit")
    public String showEditTaskPage(
            @PathVariable int projectId,
            @PathVariable int taskId,
            @PathVariable int subprojectId,
            Model model
    ) {

      try {
          Task task = taskService.getTaskById(taskId,subprojectId);
          model.addAttribute("task", task);
          model.addAttribute("assignee", task.getAssignee()); // now safe
          model.addAttribute("projectId", projectId);

      } catch (Exception e) {
          return "redirect:/projectDashboard" + projectId;
      }

        return "editTask";
    }

    @PostMapping("/{taskId}/{projectId}/edit")
    public String updateTask(
            @PathVariable int projectId,
            @PathVariable int taskId,
            @RequestParam String title,
            @RequestParam String description,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate deadline,
            Model model
    ) {
        taskService.updateTask(taskId, title, description, deadline);
        return "redirect:/projectDashboard/" + projectId;
    }


    @PostMapping("/{taskId}/assign-user")
    public String assignUserToTask(
            @PathVariable int taskId,
            @RequestParam String userEmail,
            RedirectAttributes redirectAttributes
    ) {
        boolean success = taskService.addUserToTask(taskId, userEmail);

        if (!success) {
            redirectAttributes.addFlashAttribute("errorMessage", "User not found or already assigned.");
        }

        return "redirect:/task/" + taskId + "/edit";
    }

    @PostMapping("/{taskId}/remove-user")
    public String removeUserFromTask(
            @PathVariable int taskId,
            RedirectAttributes redirectAttributes
    ) {
        Task task = taskService.getTaskById(taskId, 0);
        if (task.getAssignee() != null) {
            taskService.removeUserFromTask(taskId, task.getAssignee().getEmail());
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "No user assigned to this task.");
        }

        return "redirect:/task/" + taskId + "/edit";
    }
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
}

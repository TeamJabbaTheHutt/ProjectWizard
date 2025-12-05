package com.hauxy.projectwizard.controller;

import com.hauxy.projectwizard.model.Subtask;
import com.hauxy.projectwizard.model.Task;
import com.hauxy.projectwizard.model.User;
import com.hauxy.projectwizard.service.SubtaskService;
import com.hauxy.projectwizard.service.TaskService;
import org.springframework.format.annotation.DateTimeFormat;
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

    public TaskController(TaskService taskService, SubtaskService subtaskService) {
        this.taskService = taskService;
        this.subtaskService = subtaskService;
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
@GetMapping("/create/{projectId}/{parentId}")
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


    // har vi behov for subproject ID?
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

    @GetMapping("/subtask/{subtaskId}/{projectId}/{parentId}/edit")
    public String showEditSubTaskPage(
            @PathVariable int subtaskId,
            @PathVariable int projectId,
            @PathVariable int parentId,
            Model model
    ) {

        try {
//            Subtask subTask = subtaskService.getSubtaskById(subtaskId);
            Subtask subTask = subtaskService.getSubtaskById(subtaskId, parentId);
            model.addAttribute("subtask", subTask);
//            model.addAttribute("assignee", subTask.getAssignee()); // now safe
            model.addAttribute("projectId", projectId);

        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/projectDashboard" + projectId;
        }

        return "editSubtask";
    }

    @PostMapping("/subtask/{subtaskId}/{projectId}/edit")
    public String updateSubTask(
            @PathVariable int projectId,
            @PathVariable int subtaskId,
            @RequestParam String title,
            @RequestParam String description,
            Model model
    ) {
        subtaskService.updateSubTask(subtaskId, title, description);
        return "redirect:/projectDashboard/" + projectId;
    }

    @PostMapping("/subtask/{subtaskId}/{projectId}/{parentId}/assign-user")
    public String assignUserToSubTask(
            @PathVariable int subtaskId,
            @PathVariable int projectId,
            @PathVariable int parentId,
            @RequestParam String userEmail,
            RedirectAttributes redirectAttributes
    ) {
        boolean success = subtaskService.addUserToSubTask(subtaskId, userEmail);

        if (!success) {
            redirectAttributes.addFlashAttribute("errorMessage", "User not found or already assigned.");
        }

        return "redirect:/task/subtask/" + subtaskId + "/" + projectId + "/" + parentId +"/edit";
    }

        @PostMapping("/subtask/{subtaskId}/{projectId}/{parentId}/remove-user")
        public String removeUserFromSubTask(
                @PathVariable int subtaskId,
                @PathVariable int projectId,
                @PathVariable int parentId,
                RedirectAttributes redirectAttributes
        ) {
            Subtask subtask = subtaskService.getSubtaskById(subtaskId, parentId);
            if (subtask.getAssignee() != null) {
                subtaskService.removeUserFromSubTask(subtaskId);
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "No user assigned to this task.");
            }

            return "redirect:/task/subtask/" + subtaskId + "/" + projectId + "/" + parentId +"/edit";
        }




}
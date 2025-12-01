package com.hauxy.projectwizard.controller;

import com.hauxy.projectwizard.service.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/task")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/createTask")
    public String createTask() {
        return "createTask";
    }


    @PostMapping("/create")
    public String createTask(@RequestParam String title,
                             @RequestParam String description,
                             @RequestParam int projectId,
                             @RequestParam(required = false) Integer parentTaskId) {

        taskService.createTask(title, description, projectId, parentTaskId);

        return "redirect:/projectDashboard/" + projectId;
    }


}

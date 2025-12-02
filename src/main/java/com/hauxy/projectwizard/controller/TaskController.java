package com.hauxy.projectwizard.controller;

import com.hauxy.projectwizard.model.Subtask;
import com.hauxy.projectwizard.model.Task;
import com.hauxy.projectwizard.service.SubtaskService;
import com.hauxy.projectwizard.service.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    task.setProjectId(projectId);  // ✅ set it here
    task.setParentId(parentId);
    model.addAttribute("task", task);


    return "createTask";
}

    @PostMapping("/update")
    public String updateSubtask(@ModelAttribute Subtask subtask) {
        subtaskService.updateSubtask(subtask);
        return "redirect:/projectDashboard/" + subtask.getProjectId();
    }


    @PostMapping("/saveTask")
    public String saveTask(@ModelAttribute Task task) {
        System.out.println(task.getParentId());
        taskService.createTask(
                task.getTitle(),
                task.getDescription(),
                task.getProjectId(),
                task.getParentId(),
                task.getDeadline()
        );

        return "redirect:/projectDashboard/" + task.getProjectId();
    }





}

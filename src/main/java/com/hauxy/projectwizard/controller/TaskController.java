package com.hauxy.projectwizard.controller;

import com.hauxy.projectwizard.model.Subproject;
import com.hauxy.projectwizard.model.Subtask;
import com.hauxy.projectwizard.model.Task;
import com.hauxy.projectwizard.service.SubprojectService;
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
}

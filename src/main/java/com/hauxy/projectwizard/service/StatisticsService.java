package com.hauxy.projectwizard.service;

import com.hauxy.projectwizard.model.Project;
import com.hauxy.projectwizard.model.Subproject;
import com.hauxy.projectwizard.model.Subtask;
import com.hauxy.projectwizard.model.Task;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatisticsService {
    private final TaskService taskService;
    private final ProjectService projectService;
    private final SubprojectService subprojectService;
    private final SubtaskService subtaskService;

    public StatisticsService(TaskService taskService, ProjectService projectService, SubprojectService subprojectService, SubtaskService subtaskService) {
        this.taskService = taskService;
        this.projectService = projectService;
        this.subprojectService = subprojectService;
        this.subtaskService = subtaskService;
    }

    // ud fra problem formulering:
    /// summering af tidsforbrug, så man kan få overblik over tidsforbrug på projekter og delprojekter mv
    ///  fordeling af tidsforbrug på arbejdsdage, så man ved hvor mange timer der skal arbejdes hver dag, for at projektet bliver færdigt til tiden.

    // total estimering af alle tasks og subtasks af estimated time
    public double timeEstimatedOnAllTasksAndSubtasks(int projectId) {
        Project project = projectService.getProjectByProjectId(projectId);
        double result = 0.0;
        List<Task> allTasksToProject = taskService.getAllTasksByProjectId(projectId);
        List<Subtask> AllSubTasksToProject = subtaskService.getAllSubTasksByProjectId(projectId);
        List<Subproject> allSubProjects = subprojectService.getAllSubProjectsByProjectId(projectId);

        System.out.println("AllSubTasksToProject: " + AllSubTasksToProject.toString());
        System.out.println("AllSubProjects: " + allSubProjects.toString());
        System.out.println("AllTasksToProject: " + allTasksToProject.toString());

        return result;
    }



    // total faktisk forbrugt tid af time actual af tasks og subtasks

    // afvigelse, er vi i timer plus eller minus?

    // days until done ud fra deadline og localdate.now()

    // tasks completed i done ud fra hvor mange tasks der er i total

}

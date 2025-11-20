package com.hauxy.projectwizard.service;

import com.hauxy.projectwizard.model.Project;
import com.hauxy.projectwizard.model.Subproject;
import com.hauxy.projectwizard.model.Task;
import org.springframework.stereotype.Service;

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
}

package com.hauxy.projectwizard.service;

import com.hauxy.projectwizard.model.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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
        double result = 0.0;
        List<Task> allTasksToProject = taskService.getAllTasksByProjectId(projectId);
        List<Subtask> AllSubTasksToProject = subtaskService.getAllSubTasksByProjectId(projectId);
        for (Task task : allTasksToProject) {
            result += task.getEstimate();
        }
        for (Subtask subtask : AllSubTasksToProject) {
            result += subtask.getEstimate();
        }
        return result;
    }



    // total faktisk forbrugt tid af time actual af tasks og subtasks
    public double timeActualUsedForAllTasksAndSubtasks(int projectId) {
        double result = 0.0;
        List<Task> allTasksToProject = taskService.getAllTasksByProjectId(projectId);
        List<Subtask> AllSubTasksToProject = subtaskService.getAllSubTasksByProjectId(projectId);

        for(Task task : allTasksToProject) {
            result += task.getActualTime();
        }
        for (Subtask subtask : AllSubTasksToProject) {
            result += subtask.getActualTime();
        }
        return result;
    }
    // afvigelse, er vi i timer plus eller minus?
    public double timeDifferenceOnAllTasksAndSubtasks(int projectId) {
        double totalEstimate = 0.0;
        double totalActual = 0.0;

        List<Task> allTasksToProject = taskService.getAllTasksByProjectId(projectId);
        List<Subtask> allSubTasksToProject = subtaskService.getAllSubTasksByProjectId(projectId);

        for (Task task : allTasksToProject) {
            totalEstimate += task.getEstimate();
            totalActual += task.getActualTime();
        }

        for (Subtask subtask : allSubTasksToProject) {
            totalEstimate += subtask.getEstimate();
            totalActual += subtask.getActualTime();
        }

        double timeDifference = totalActual - totalEstimate;
        return timeDifference;
    }
    // days until done ud fra deadline og localdate.now()
    public double daysUntilDeadlineProject(int projectId, LocalDate now) {
        Project project = projectService.getProjectByProjectId(projectId);
        LocalDate deadline = project.getDeadline();

        if (deadline == null) {
            throw new IllegalArgumentException("Project has no deadline set");
        }

        return (double) ChronoUnit.DAYS.between(now, deadline);
    }

    public int daysUntilDeadLineSubProject(int subProjectId, int projectId, LocalDate now) {
        List<Subproject> subprojects = subprojectService.getAllSubProjectsByProjectId(projectId);

        for (Subproject subproject : subprojects) {
            if (subproject.getSubProjectId() == subProjectId) {
                LocalDate deadline = subproject.getDeadline();

                if (deadline == null) {
                    throw new IllegalArgumentException("Subproject has no deadline set");
                }

                return (int) ChronoUnit.DAYS.between(now, deadline);
            }
        }

        return -1;
    }

    // tasks completed in done ud fra hvor mange tasks der er i total

    public int tasksInDoneByProjectId(int projectId) {
        List<Task> allTasksToProject = taskService.getAllTasksByProjectId(projectId);
        List<Subtask> allSubTasksToProject = subtaskService.getAllSubTasksByProjectId(projectId);
        int tasksInDone = 0;
        for (Task task : allTasksToProject) {

            if (task.getStatus() == Status.Done) {
                tasksInDone += 1;
            }
        }
        for(Subtask subtask : allSubTasksToProject) {

            if (subtask.getStatus() == Status.Done) {
                tasksInDone += 1;
            }
        }
        return tasksInDone;
    }

    public int totalTasksByProjectId(int projectId) {
        List<Task> allTasksToProject = taskService.getAllTasksByProjectId(projectId);
        List<Subtask> allSubTasksToProject = subtaskService.getAllSubTasksByProjectId(projectId);
        int totalTasksInBacklog = 0;
        for (Task task : allTasksToProject) {
            totalTasksInBacklog += 1;
        }
        for (Subtask subtask : allSubTasksToProject) {
            totalTasksInBacklog += 1;
        }
        return totalTasksInBacklog;
    }

    public int differenceOfTasksDoneToTotalTasksByProjectId(int projectId) {
        return totalTasksByProjectId(projectId)-tasksInDoneByProjectId(projectId);
    }

    public String formatDeadlineDays(double days) {
        if (days < 0) {
            return Math.abs(days) + " days over deadline";
        } else if (days > 0) {
            return days + " days until deadline";
        } else {
            return "Deadline is today";
        }
    }
}

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

    // total estimering af alle tasks af estimated time
    public double timeEstimatedOnAllTasksAndSubtasks(int projectId) {
        double result = 0.0;
        List<Task> allTasksToProject = projectService.getAllTasksByProjectId(projectId);

        for (Task task : allTasksToProject) {
            result += task.getEstimate();
        }

        return result;
    }

    public double timeEstimatedOnTasksInDone(int projectId) {
        double result = 0.0;
        List<Task> allTasksToProject = projectService.getAllTasksByProjectId(projectId);
        for (Task task : allTasksToProject) {
            if (task.getStatus() == Status.Done) {
                result += task.getEstimate();
            }
        }
        return result;
    }



    // total faktisk forbrugt tid af time actual af tasks
    public double timeActualUsedForAllTasksAndSubtasks(int projectId) {
        double result = 0.0;
        List<Task> allTasksToProject = projectService.getAllTasksByProjectId(projectId);


        for(Task task : allTasksToProject) {
            if(task.getStatus() == Status.Done) {
                result += task.getActualTime();
            }

        }
        return result;
    }
    // afvigelse, er vi i timer plus eller minus?
    public double timeDifferenceOnAllTasksAndSubtasks(int projectId) {
        double totalEstimate = 0.0;
        double totalActual = 0.0;

        List<Task> allTasksToProject = projectService.getAllTasksByProjectId(projectId);

        for (Task task : allTasksToProject) {
            totalEstimate += task.getEstimate();
            totalActual += task.getActualTime();
        }


        return totalEstimate - totalActual;

    }


    // days until done ud fra deadline og localdate.now()
    public double daysUntilDeadlineProject(int projectId, LocalDate now) {
        Project project = projectService.getProjectById(projectId);
        LocalDate deadline = project.getDeadline();

        if (deadline == null) {
            throw new IllegalArgumentException("Project has no deadline set");
        }

        return (double) ChronoUnit.DAYS.between(now, deadline);
    }



    // tasks completed in done ud fra hvor mange tasks der er i total

    public int tasksInDoneByProjectId(int projectId) {
        List<Task> allTasksToProject = projectService.getAllTasksByProjectId(projectId);
        List<Subtask> allSubTasksToProject = projectService.getAllSubTasksByProjectId(projectId);
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

    public double hoursLeftFromTasksNotInDoneByProjectId(int projectId) {
        List<Task> allTasksToProject = projectService.getAllTasksByProjectId(projectId);
        List<Subtask> allSubTasksToProject = projectService.getAllSubTasksByProjectId(projectId);
        double totalHoursLeft = 0;

        for (Task task : allTasksToProject) {

            if (task.getStatus() != Status.Done) {
                totalHoursLeft += task.getEstimate();
            }
        }
        return totalHoursLeft;
    }

    public int totalTasksByProjectId(int projectId) {
        List<Task> allTasksToProject = projectService.getAllTasksByProjectId(projectId);
        return allTasksToProject.size();
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
    public LocalDate getCreatedAtByProjectId(int projectId) {
        Project project = projectService.getProjectById(projectId);
        return project.getCreatedAt();
    }
    public LocalDate getDeadLineByProjectId(int projectId) {
        Project project = projectService.getProjectById(projectId);
        return project.getDeadline();
    }


    public double getPercentageOfProjectDone(int projectId) {

        LocalDate startDate = getCreatedAtByProjectId(projectId);
        LocalDate endDate = getDeadLineByProjectId(projectId);
        LocalDate today = LocalDate.now();

        double totalDays = ChronoUnit.DAYS.between(startDate, endDate);

        double passedDays = ChronoUnit.DAYS.between(startDate, today);

        if (totalDays <= 0) {
            return 0;
        }

        double percentage = (passedDays / totalDays) * 100;
        return Math.max(0, Math.min(100, percentage));
    }

    public double getPercentageOfTasksDone(int  projectId) {
        int totalTasks = totalTasksByProjectId(projectId);
        int totalTasksInDone = tasksInDoneByProjectId(projectId);

        double percentage = 0;

        if (totalTasks > 0) {
            percentage = ((double) totalTasksInDone / totalTasks) * 100;
        }

        percentage = Math.max(0, Math.min(100, percentage));
        return percentage;
    }


    public int getNumberOfSubprojects(int projectId) {
        List<Subproject> subprojects = projectService.getAllSubProjectsByProjectId(projectId);
        return subprojects.size();
    }

    public int getNumberOfTasksInSubProjectsInDone(int subprojectId) {
        List<Task> tasks = taskService.getAllTasksBySubprojectId(subprojectId);
        int result = 0;
        for (Task task : tasks) {
            if (task.getStatus() == Status.Done) {
                result += 1;
            }
        }
        return result;

    }
    public int getAllTasksInSubproject(int subprojectId) {
        List<Task> tasks = taskService.getAllTasksBySubprojectId(subprojectId);
        return tasks.size();
    }
    public List<Subproject> getSubprojects(int projectId) {
        return subprojectService.getAllSubProjectsByProjectId(projectId);
    }

    public double estimatedHoursForSubproject(int subprojectId) {
        List<Task> tasks = taskService.getAllTasksBySubprojectId(subprojectId);
        double result = 0;
        for (Task task : tasks) {
            result +=  task.getEstimate();
        }
        return result;
    }
    public double actualHoursForSubproject(int subprojectId) {
        List<Task> tasks = taskService.getAllTasksBySubprojectId(subprojectId);
        double result = 0;
        for (Task task : tasks) {
            result +=  task.getActualTime();
        }
        return result;
    }

}

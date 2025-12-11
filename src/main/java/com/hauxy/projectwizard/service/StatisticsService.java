package com.hauxy.projectwizard.service;

import com.hauxy.projectwizard.model.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class StatisticsService {
    private final TaskService taskService;
    private final ProjectService projectService;
    private final SubprojectService subprojectService;
    private final SubtaskService subtaskService;

    private Project project;
    private List<Subproject> subprojects;
    private List<Task> tasks;
    private List<Subtask> subtasks;


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
    public void fetchData(int projectId) {
        this.project = projectService.getProjectById(projectId);
        this.subprojects = projectService.getAllSubProjectsByProjectId(projectId);
        this.tasks = projectService.getAllTasksByProjectId(projectId);
        this.subtasks = projectService.getAllSubTasksByProjectId(projectId);
    }


    public double timeEstimatedOnAllTasksAndSubtasks() {
        double result = 0.0;

        for (Task task : tasks) {
            result += task.getEstimate();
        }
        return result;
    }

    public double timeEstimatedOnTasksInDone() {
        double result = 0.0;
        for (Task task : tasks) {
            if (task.getStatus() == Status.Done) {
                result += task.getEstimate();
            }
        }
        return result;
    }



    // total faktisk forbrugt tid af time actual af tasks
    public double timeActualUsedForAllTasksAndSubtasks() {
        int num = 0;
        double result = 0.0;


        for(Task task : tasks) {
            if(task.getStatus() == Status.Done) {
                result += task.getActualTime();
                num++;
            }

        }
        System.out.println(num);
        return result;
    }
    // afvigelse, er vi i timer plus eller minus?
    public double timeDifferenceOnAllTasksAndSubtasks() {
        double totalEstimate = 0.0;
        double totalActual = 0.0;

        for (Task task : tasks) {
            if (task.getStatus() == Status.Done) {
                totalEstimate += task.getEstimate();
                totalActual += task.getActualTime();
            }
        }


        return totalEstimate - totalActual;

    }


    // days until done ud fra deadline og localdate.now()
    public double daysUntilDeadlineProject(int projectId, LocalDate now) {
        LocalDate deadline = project.getDeadline();

        if (deadline == null) {
            throw new IllegalArgumentException("Project has no deadline set");
        }

        return (double) ChronoUnit.DAYS.between(now, deadline);
    }



    // tasks completed in done ud fra hvor mange tasks der er i total

    public int tasksInDoneByProjectId() {
        int tasksInDone = 0;
        for (Task task : tasks) {

            if (task.getStatus() == Status.Done) {
                tasksInDone += 1;
            }
        }

        return tasksInDone;
    }

    public double hoursLeftFromTasksNotInDoneByProjectId() {
        double totalHoursLeft = 0;

        for (Task task : tasks) {

            if (task.getStatus() != Status.Done) {
                totalHoursLeft += task.getEstimate();
            }
        }
        return totalHoursLeft;
    }

    public int totalTasksByProjectId() {

        return tasks.size();
    }


    public String formatDeadlineDays() {
        double days = daysUntilDeadlineProject(project.getProjectId(), LocalDate.now());
        if (days < 0) {
            return Math.abs(days) + " days over deadline";
        } else if (days > 0) {
            return days + " days until deadline";
        } else {
            return "Deadline is today";
        }
    }
    public LocalDate getCreatedAtByProjectId() {
        return project.getCreatedAt();
    }
    public LocalDate getDeadLineByProjectId() {
        return project.getDeadline();
    }


    public double getPercentageOfProjectDone() {

        LocalDate startDate = getCreatedAtByProjectId();
        LocalDate endDate = getDeadLineByProjectId();
        LocalDate today = LocalDate.now();

        double totalDays = ChronoUnit.DAYS.between(startDate, endDate);

        double passedDays = ChronoUnit.DAYS.between(startDate, today);

        if (totalDays <= 0) {
            return 0;
        }

        double percentage = (passedDays / totalDays) * 100;
        return Math.max(0, Math.min(100, percentage));
    }

    public double getPercentageOfTasksDone() {
        int totalTasks = totalTasksByProjectId();
        int totalTasksInDone = tasksInDoneByProjectId();

        double percentage = 0;

        if (totalTasks > 0) {
            percentage = ((double) totalTasksInDone / totalTasks) * 100;
        }

        percentage = Math.max(0, Math.min(100, percentage));
        return percentage;
    }


    public int getNumberOfSubprojects() {
        return subprojects.size();
    }

    public int getNumberOfTasksInSubProjectsInDone(int subprojectId) {
        int result = 0;
        for (Task task : tasks) {
            if (task.getParentId() == subprojectId) {
                if (task.getStatus() == Status.Done) {
                    result += 1;
                }
            }

        }
        return result;

    }
    public int getAllTasksInSubproject(int subprojectId) {
        int total = 0;
        for (Task task : tasks) {
            if (task.getParentId() == subprojectId) {
                total += 1;
            }
        }
        return total;
    }
    public List<Subproject> getSubprojects() {
        return subprojects;
    }

    public double estimatedHoursForSubproject(int subprojectId) {
        double result = 0;
        for (Task task : tasks) {
            if (task.getParentId() == subprojectId) {
                result +=  task.getEstimate();

            }
        }
        return result;
    }
    public double actualHoursForSubproject(int subprojectId) {
        double result = 0;
        for (Task task : tasks) {
            if(task.getParentId() == subprojectId){
                if (task.getStatus() == Status.Done) {
                    result +=  task.getActualTime();
                }
            }

        }
        return result;
    }

    public List<User> getMembers() {
        return projectService.getProjectMembers(project.getProjectId());
    }

}

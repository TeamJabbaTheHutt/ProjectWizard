package com.hauxy.projectwizard.controller;

import com.hauxy.projectwizard.exceptions.UserNotLoggedInException;
import com.hauxy.projectwizard.model.Subproject;
import com.hauxy.projectwizard.model.User;
import com.hauxy.projectwizard.service.StatisticsService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/statistics")
public class StatisticsController {
    private final StatisticsService statisticsService;

    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GetMapping("/project/{projectId}")
    public String projectStatistics(@PathVariable("projectId") int projectId, Model model, HttpSession httpSession) {
        try {
            User user = (User) httpSession.getAttribute("loggedInUser");
            model.addAttribute("timeEstimatedOnAllTasksAndSubtasks", statisticsService.timeEstimatedOnAllTasksAndSubtasks(projectId));
            model.addAttribute("timeActualUsedForAllTasksAndSubtasks", statisticsService.timeActualUsedForAllTasksAndSubtasks(projectId));
            model.addAttribute("timeDifferenceOnAllTasksAndSubtasks", statisticsService.timeDifferenceOnAllTasksAndSubtasks(projectId));
            model.addAttribute("dateToday", LocalDate.now());
//            model.addAttribute("tasksDoneByProjectId", statisticsService.tasksInDoneByProjectId(projectId));
//            model.addAttribute("totalTasksByProjectId", statisticsService.totalTasksByProjectId(projectId));
//            model.addAttribute("differenceOfTasksDoneToTotalTasksByProjectId", statisticsService.differenceOfTasksDoneToTotalTasksByProjectId(projectId));
            model.addAttribute("daysUntilDeadlineProject", statisticsService.formatDeadlineDays(statisticsService.daysUntilDeadlineProject(projectId, LocalDate.now())));
            model.addAttribute("percentageOfProjectInDays", statisticsService.getPercentageOfProjectDone(projectId));
            System.out.println(statisticsService);
            // get all subprojects for stats?
            return "projectStatistics";
        } catch (NullPointerException e) {
            throw new UserNotLoggedInException("you might not be logged in", e);

        }
    }

}

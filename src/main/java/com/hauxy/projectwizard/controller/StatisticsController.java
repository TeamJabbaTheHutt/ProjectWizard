package com.hauxy.projectwizard.controller;

import com.hauxy.projectwizard.exceptions.UserNotLoggedInException;
import com.hauxy.projectwizard.model.Project;
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
            user.getUserId();
            // check om user er del af projektet?

        } catch (NullPointerException e) {
            throw new UserNotLoggedInException("you might not be logged in", e);

        }
        statisticsService.fetchData(projectId);

        // bruger springbean expression til at håndtere det store frontend call til statistikkerne.

        model.addAttribute("dateToday", LocalDate.now());
        model.addAttribute("daysUntilDeadlineProject", statisticsService.formatDeadlineDays(statisticsService.daysUntilDeadlineProject(projectId, LocalDate.now())));
        model.addAttribute("subprojects", statisticsService.getSubprojects());

        return "projectStatistics";

    }

}

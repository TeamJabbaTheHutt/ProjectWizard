package com.hauxy.projectwizard.controller;

import com.hauxy.projectwizard.exceptions.DatabaseOperationException;
import com.hauxy.projectwizard.exceptions.UserNotLoggedInException;
import com.hauxy.projectwizard.model.Project;
import com.hauxy.projectwizard.model.Subproject;
import com.hauxy.projectwizard.model.User;
import com.hauxy.projectwizard.service.LoginService;
import com.hauxy.projectwizard.service.StatisticsService;
import jakarta.servlet.http.HttpSession;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
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
    private final LoginService loginService;
    private User user;
    private HttpSession session;

    public StatisticsController(StatisticsService statisticsService, LoginService loginService, HttpSession session) {
        this.statisticsService = statisticsService;
        this.loginService = loginService;
        this.session = session;
    }

    @GetMapping("/project/{projectId}")
    public String projectStatistics(@PathVariable("projectId") int projectId, Model model) {
        user = loginService.checkIfLoggedInAndGetUser(session);

        statisticsService.fetchData(projectId);
        // bruger springbean expression til at håndtere det store frontend call til statistikkerne.
        model.addAttribute("dateToday", LocalDate.now());
        return "projectStatistics";



    }

}

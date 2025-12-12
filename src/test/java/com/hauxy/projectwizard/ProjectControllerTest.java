package com.hauxy.projectwizard;


import com.hauxy.projectwizard.controller.ProjectController;
import com.hauxy.projectwizard.model.Project;
import com.hauxy.projectwizard.model.User;
import com.hauxy.projectwizard.service.*;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import java.time.LocalDate;
import java.util.List;


import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProjectController.class)
public class ProjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProjectService projectService;
    @MockitoBean
    private UserService userService;
    @MockitoBean
    private LoginService loginService;
    @MockitoBean
    private StatisticsService statisticsService;
    @MockitoBean
    private SubtaskService subtaskService;
    @MockitoBean
    private TaskService taskService;
    @MockitoBean
    private SubprojectService subprojectService;



    @Test
    void getProjectByUserIdTestListContainsProjects() throws Exception {
        // Arrange
        User fakeUser = new User();
        fakeUser.setUserId(1);

        Project project = new Project();
        project.setProjectId(1);
        project.setTitle("Test Project");
        project.setDescription("Description");
        project.setDeadline(LocalDate.of(2025, 1, 1));

        List<Project> projects = List.of(project);


        when(loginService.checkIfLoggedInAndGetUser(any(HttpSession.class))).thenReturn(fakeUser);
        when(projectService.getUsersProjectsByUserId(1)).thenReturn(projects);

        // Act & Assert
        mockMvc.perform(get("/project/home"))
                .andExpect(status().isOk())
                .andExpect(view().name("homepage"))
                .andExpect(model().attribute("UsersListOfProjects", projects));

        // Verify
        verify(loginService).checkIfLoggedInAndGetUser(any(HttpSession.class));
        verify(projectService).getUsersProjectsByUserId(1);
    }




}

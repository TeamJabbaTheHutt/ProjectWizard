package com.hauxy.projectwizard;

import com.hauxy.projectwizard.controller.ProjectController;
import com.hauxy.projectwizard.model.*;
import com.hauxy.projectwizard.service.ProjectService;
import com.hauxy.projectwizard.service.SubprojectService;
import com.hauxy.projectwizard.service.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProjectController.class)
class ProjectDashboardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProjectService projectService;

    @MockitoBean
    private SubprojectService subprojectService;

    @MockitoBean
    private TaskService taskService;

    @Test
    void testProjectDashboardRendersCorrectly() throws Exception {
        // Mock project
        Project project = new Project();
        project.setProjectId(1);
        project.setTitle("Project Test");

        // Mock subprojects
        Subproject sp = new Subproject();
        sp.setSubProjectId(101);
        sp.setTitle("Subproject 1");
        sp.setDescription("Subproject Desc");
        sp.setCreatedAt(LocalDate.of(2024, 1, 1));
        sp.setDeadline(LocalDate.of(2025, 1, 1));

        // Mock tasks
        Task task = new Task();
        task.setTaskId(201);
        task.setTitle("Task 1");
        task.setDescription("Task Desc");
        task.setStatus(Status.NoStatus);
        task.setEstimate(3.5);
        task.setActualTime(1.2);
        task.setDeadline(LocalDate.of(2025, 1, 10));

        sp.setTasks(List.of(task));

        // Mock service layer
        when(projectService.getProjectById(1)).thenReturn(project);
        when(subprojectService.getAllSubProjectsByProjectId(1)).thenReturn(List.of(sp));

        // Perform GET request
        mockMvc.perform(get("/project/1")
                        .sessionAttr("loggedInUser", new User()))
                .andExpect(status().isOk())
                .andExpect(view().name("projectDashboard"))
                .andExpect(model().attributeExists("project"))
                .andExpect(model().attributeExists("subProjects"))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Project Test")))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Subproject 1")))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Task 1")));
    }
}

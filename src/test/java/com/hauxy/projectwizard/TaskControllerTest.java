package com.hauxy.projectwizard;

import com.hauxy.projectwizard.controller.TaskController;
import com.hauxy.projectwizard.service.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

@WebMvcTest(TaskController.class)
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TaskService taskService;

    @Test
    void checkIfTaskCreateRedirectToProjectsDashboard() throws Exception {
        int projectId = 42;

        when(taskService.createTask("Test Task", "This is a test task", projectId, null))
                .thenReturn(1);

        mockMvc.perform(post("/task/create")
                        .param("title", "Test Task")
                        .param("description", "This is a test task")
                        .param("projectId", String.valueOf(projectId))
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/projectDashboard/" + projectId));
        verify(taskService).createTask("Test Task", "This is a test task", projectId, null);
    }

    @Test
    void checkIfSubtaskCreateRedirectToProjectsDashboard() throws Exception {
        int projectId = 42;
        int parentTaskId = 7;


        when(taskService.createTask("Subtask", "Subtask description", projectId, parentTaskId))
                .thenReturn(1);

        mockMvc.perform(post("/task/create")
                        .param("title", "Subtask")
                        .param("description", "Subtask description")
                        .param("projectId", String.valueOf(projectId))
                        .param("parentTaskId", String.valueOf(parentTaskId))
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/projectDashboard/" + projectId));
        verify(taskService).createTask("Subtask", "Subtask description", projectId, parentTaskId);
    }
}

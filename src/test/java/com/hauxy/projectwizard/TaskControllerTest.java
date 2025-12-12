package com.hauxy.projectwizard;

import com.hauxy.projectwizard.controller.TaskController;
import com.hauxy.projectwizard.model.*;
import com.hauxy.projectwizard.service.LoginService;
import com.hauxy.projectwizard.service.SubprojectService;
import com.hauxy.projectwizard.service.SubtaskService;
import com.hauxy.projectwizard.service.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import java.time.LocalDate;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskController.class)
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TaskService taskService;
    @MockitoBean
    private SubtaskService subtaskService;
    @MockitoBean
    private SubprojectService subprojectService;
    @MockitoBean
    private LoginService loginService;


    @Test
    void checkIfTaskCreateRedirectToProjectsDashboard() throws Exception {
        mockMvc.perform(get("/task/createTask/1/1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("task"))
                .andExpect(view().name("createTask"));

    }


    @Test
    void testSaveTask() throws Exception {
        Task task = new Task("test", "testdesc");
        task.setParentId(1);
        task.setDeadline(LocalDate.now());

        when(        taskService.createTask(
                task.getTitle(),
                task.getDescription(),
                task.getParentId(),
                task.getDeadline()
        )).thenReturn(1);

        mockMvc.perform(post("/task/saveTask/1"))
                .andExpect(status().is3xxRedirection());

    }

    @Test
    void checkIfSubtaskCreateRedirectToProjectsDashboard() throws Exception {
        mockMvc.perform(get("/task/createSubtask/1/1"))
                .andExpect(model().attributeExists("subtask"))
                .andExpect(status().isOk())
                .andExpect(view().name("createSubtask"));

    }

    @Test
    void testSaveSubtask() throws Exception {
        mockMvc.perform(post("/task/saveSubtask/1")
                        .param("title", "Test Subtask")
                        .param("description", "Test description"))
                .andExpect(status().is3xxRedirection());

        verify(subtaskService).createSubtask(any(Subtask.class));
    }

}

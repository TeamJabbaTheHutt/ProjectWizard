package com.hauxy.projectwizard;


import com.hauxy.projectwizard.model.Subtask;
import com.hauxy.projectwizard.model.Task;
import com.hauxy.projectwizard.service.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StatisticsServiceTest {
    @Mock
    private SubprojectService subprojectService;
//    @Mock
//    private ProjectService projectService;
    @Mock
    private SubtaskService subtaskService;
    @Mock
    private TaskService taskService;
    @InjectMocks
    private StatisticsService statisticsService;

    @Test
    public void timeEstimatedOnAllTasksAndSubtasksTest() {
        int projectId = 1;

        // ARRANGE
        Task t1 = new Task();
        t1.setEstimate(50.1);

        Task t2 = new Task();
        t2.setEstimate(40.1);

        Subtask s1 = new Subtask();
        s1.setEstimate(10.0);

        List<Task> tasks = List.of(t1, t2);
        List<Subtask> subtasks = List.of(s1);

        when(taskService.getAllTasksByProjectId(projectId))
                .thenReturn(tasks);

        when(subtaskService.getAllSubTasksByProjectId(projectId))
                .thenReturn(subtasks);

        // ACT
        double result = statisticsService.timeEstimatedOnAllTasksAndSubtasks(projectId);

        // ASSERT
        assertThat(result).isEqualTo(100.2);

        verify(taskService).getAllTasksByProjectId(projectId);
        verify(subtaskService).getAllSubTasksByProjectId(projectId);
    }






}

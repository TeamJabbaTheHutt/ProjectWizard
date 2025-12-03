package com.hauxy.projectwizard;

import com.hauxy.projectwizard.service.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import java.time.LocalDate;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@SpringBootTest
@ActiveProfiles("test")
@Sql(scripts = "classpath:h2init.sql", executionPhase  = BEFORE_TEST_METHOD)
public class StatisticsServiceIntegrationTest {

    @Autowired
    StatisticsService statisticsService;

    @Autowired
    TaskService taskService;
    @Autowired
    SubtaskService subtaskService;
    @Autowired
    ProjectService projectService;
    @Autowired
    SubprojectService subprojectService;


    @Test
    public void timeEstimatedOnAllTasksAndSubtasksTest() {
        // act
        double result = statisticsService.timeEstimatedOnAllTasksAndSubtasks(1);
        // assert
        assertThat(result).isEqualTo(2.5);
    }

    @Test
    public void timeActualUsedForAllTasksAndSubtasksTest() {
        //act
        double result = statisticsService.timeActualUsedForAllTasksAndSubtasks(1);
        // assert
        assertThat(result).isEqualTo(0.0);
    }

    @Test
    public void timeDifferenceOnAllTasksAndSubtasks() {
        //act
        double result = statisticsService.timeDifferenceOnAllTasksAndSubtasks(1);

        // assert
        assertThat(result).isEqualTo(0.0);
    }

    @Test
    public void daysUntilDeadlineProject() {
        LocalDate nowTest = LocalDate.of(2025, 2, 1);
        double result = statisticsService.daysUntilDeadlineProject(1, nowTest);
        assertThat(result).isEqualTo(-31);

    }

    @Test
    public void tasksInDoneByProjectIdTest() {
        int result =  statisticsService.tasksInDoneByProjectId(1);
        assertThat(result).isEqualTo(0);
    }

    @Test
    public void hoursLeftFromTasksNotInDoneByProjectIdTest() {
        double result = statisticsService.hoursLeftFromTasksNotInDoneByProjectId(1);
        assertThat(result).isEqualTo(5.0);
    }

    @Test
    public void totalTasksByProjectId() {
        int result = statisticsService.totalTasksByProjectId(1);
        assertThat(result).isEqualTo(2);
    }

    @Test
    public void getCreatedAtByProjectIdTest() {
        LocalDate result = statisticsService.getCreatedAtByProjectId(1);
        assertThat(result).isEqualTo(LocalDate.of(2024,1,1));
    }

    @Test
    public void getDeadLineByProjectIdTest() {
        LocalDate result = statisticsService.getDeadLineByProjectId(1);
        assertThat(result).isEqualTo(LocalDate.of(2025,1,1));
    }

    @Test
    public void getPercentageOfProjectDone() {
        double  result = statisticsService.getPercentageOfProjectDone(1);
        assertThat(result).isEqualTo(100.0);
    }

    @Test
    public void getPercentageOfTasksDone() {
        double result = statisticsService.getPercentageOfTasksDone(1);
        assertThat(result).isEqualTo(0.0);
    }





}

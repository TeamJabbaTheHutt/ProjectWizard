package com.hauxy.projectwizard;

import com.hauxy.projectwizard.service.*;
import org.junit.jupiter.api.BeforeEach;
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

    @BeforeEach
    public void setup() {
        statisticsService.fetchData(1);
    }


    @Test
    public void timeEstimatedOnAllTasksAndSubtasksTest() {
        // act
        double result = statisticsService.timeEstimatedOnAllTasksAndSubtasks();
        // assert
        assertThat(result).isEqualTo(2.5);
    }

    @Test
    public void timeActualUsedForAllTasksAndSubtasksTest() {
        //act
        double result = statisticsService.timeActualUsedForAllTasksAndSubtasks();
        // assert
        assertThat(result).isEqualTo(0.0);
    }

    @Test
    public void timeDifferenceOnAllTasksAndSubtasks() {
        //act
        double result = statisticsService.timeDifferenceOnAllTasksAndSubtasks();

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
        int result =  statisticsService.tasksInDoneByProjectId();
        assertThat(result).isEqualTo(0);
    }

    @Test
    public void hoursLeftFromTasksNotInDoneByProjectIdTest() {
        double result = statisticsService.hoursLeftFromTasksNotInDoneByProjectId();
        assertThat(result).isEqualTo(2.5);
    }

    @Test
    public void totalTasksByProjectId() {
        int result = statisticsService.totalTasksByProjectId();
        assertThat(result).isEqualTo(1);
    }

    @Test
    public void getCreatedAtByProjectIdTest() {
        LocalDate result = statisticsService.getCreatedAtByProjectId();
        assertThat(result).isEqualTo(LocalDate.of(2024,1,1));
    }

    @Test
    public void getDeadLineByProjectIdTest() {
        LocalDate result = statisticsService.getDeadLineByProjectId();
        assertThat(result).isEqualTo(LocalDate.of(2025,1,1));
    }

    @Test
    public void getPercentageOfProjectDone() {
        double  result = statisticsService.getPercentageOfProjectDone();
        assertThat(result).isEqualTo(100.0);
    }

    @Test
    public void getPercentageOfTasksDone() {
        double result = statisticsService.getPercentageOfTasksDone();
        assertThat(result).isEqualTo(0.0);
    }





}

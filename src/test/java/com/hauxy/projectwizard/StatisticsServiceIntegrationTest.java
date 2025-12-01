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
        assertThat(result).isEqualTo(5.0);
    }

    @Test
    public void timeActualUsedForAllTasksAndSubtasksTest() {
        //act
        double result = statisticsService.timeActualUsedForAllTasksAndSubtasks(1);
        // assert
        assertThat(result).isEqualTo(5.0);
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
        LocalDate nowTest = LocalDate.of(2025, 2, 1); // Jan 1, 2025
        double result = statisticsService.daysUntilDeadlineProject(1, nowTest);
        assertThat(result).isEqualTo(-31);

    }

    @Test
    public void daysUntilDeadlineSubproject() {
        LocalDate nowTest = LocalDate.of(2025, 2, 1);
        int result = statisticsService.daysUntilDeadLineSubProject(1,1, nowTest);
        assertThat(result).isEqualTo(-31);

    }

    @Test
    public void tasksInDoneAndTotalTaskDifferentTest() {
        int resultTasksInDone = statisticsService.tasksInDoneByProjectId(1);
        int resultTotalTasks = statisticsService.totalTasksByProjectId(1);

        int resultDifference = statisticsService.differenceOfTasksDoneToTotalTasksByProjectId(1);

        assertThat(resultTasksInDone).isEqualTo(0);
        assertThat(resultTotalTasks).isEqualTo(2);
        assertThat(resultDifference).isEqualTo(2);
    }

}

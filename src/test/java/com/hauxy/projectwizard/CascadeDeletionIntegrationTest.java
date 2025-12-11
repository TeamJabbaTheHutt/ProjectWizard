package com.hauxy.projectwizard;
import com.hauxy.projectwizard.model.Project;
import com.hauxy.projectwizard.model.Subproject;
import com.hauxy.projectwizard.model.Subtask;
import com.hauxy.projectwizard.model.Task;
import com.hauxy.projectwizard.repository.ProjectRepository;
import com.hauxy.projectwizard.repository.SubprojectRepository;
import com.hauxy.projectwizard.repository.SubtaskRepository;
import com.hauxy.projectwizard.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.util.List;


import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@SpringBootTest
@ActiveProfiles("test") // for merge
@Sql(scripts = "classpath:h2init.sql", executionPhase = BEFORE_TEST_METHOD)
public class CascadeDeletionIntegrationTest {

    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private SubprojectRepository subprojectRepository;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private SubtaskRepository subtaskRepository;


    @Test
    void subTaskCascadeDeleteOfTaskTest() {
        Task task = new Task();
        task.setTaskId(1);

        boolean success = taskRepository.deleteTask(task);
        List<Subtask> subtasks = subtaskRepository.getAllSubtasksByProjectId(1);
        assertThat(success).isTrue();
        assertThat(subtasks).isEmpty();

    }


    @Test
    void TaskCascadeDeleteOfSubprojectTest() {
        Subproject subproject = new Subproject();
        subproject.setSubProjectId(1);

        boolean success = subprojectRepository.deleteSubproject(subproject);
        List<Task> tasks = taskRepository.getTasksByProjectId(1);
        assertThat(success).isTrue();
        assertThat(tasks).isEmpty();
    }

    @Test
    void SubprojectCascadeDeleteOfProjectTest() {
        Project project = new Project();
        project.setProjectId(1);

        boolean success = projectRepository.deleteProject(project);
        List<Subproject> subprojects = subprojectRepository.getAllSubprojectsByProjectId(1);
        assertThat(success).isTrue();
        assertThat(subprojects).isEmpty();
    }
}

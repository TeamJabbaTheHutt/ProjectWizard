package com.hauxy.projectwizard;


import com.hauxy.projectwizard.model.Project;
import com.hauxy.projectwizard.repository.ProjectRepository;
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
public class ProjectRepositoryTest {

    @Autowired
    private ProjectRepository projectRepository;

    @Test
    void getUsersProjectsByUserId() {
        var projects = projectRepository.getUsersProjectsByUserId(1);

        for (Project project : projects) {
            assertThat(projects).isNotNull();
            assertThat(project.getTitle().equals("projectTestTitle"));
            assertThat(project.getDescription().equals("projectTestDescription"));
            assertThat(project.getDeadline()).isEqualTo(LocalDate.of(2025, 1, 1));
            assertThat(project.getProjectId() == 1);
        }


    }
    @Test
    void getProjectById() {
        var project = projectRepository.getProjectById(1);

        assertThat(project).isNotNull();
        assertThat(project.getTitle().equals("projectTestTitle"));
        assertThat(project.getDescription().equals("projectTestDescription"));
        assertThat(project.getCreatedAt()).isEqualTo(LocalDate.of(2024, 1, 1));
        assertThat(project.getDeadline()).isEqualTo(LocalDate.of(2025, 1, 1));
        assertThat(project.getProjectId() == 1);



    }

    @Test
    void getAllProjects() {

        var projects = projectRepository.getUsersProjectsByUserId(1);

        for (Project project : projects) {
            assertThat(projects).isNotNull();
            assertThat(project.getTitle().equals("projectTestTitle"));
            assertThat(project.getDescription().equals("projectTestDescription"));
            assertThat(project.getCreatedAt()).isEqualTo(LocalDate.of(2024, 1, 1));
            assertThat(project.getDeadline()).isEqualTo(LocalDate.of(2025, 1, 1));
            assertThat(project.getProjectId() == 1);
        }

    }

    @Test
    void removeProject() {

        Project projectTest = new Project();
        projectTest.setProjectId(1);
        boolean success = projectRepository.deleteProject(projectTest);

        assertThat(success).isTrue();

        List<Project> projects = projectRepository.getAllProjects();

        for (Project project : projects) {
            assertThat(project.getProjectId()).isNotEqualTo(1);
        }

    }
}

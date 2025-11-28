package com.hauxy.projectwizard;


import com.hauxy.projectwizard.model.Project;
import com.hauxy.projectwizard.repository.ProjectRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;



import java.time.LocalDate;


import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@SpringBootTest
@ActiveProfiles("test")
@Sql(scripts = "classpath:h2init.sql", executionPhase = BEFORE_TEST_METHOD)
public class ProjectRepositoryTest {

    @Autowired
    private ProjectRepository projectRepository;

    @Test
    void getUsersProjectsByUserId() {
        // Arrange
        var projects = projectRepository.getUsersProjectsByUserId(1);

        // act and assert
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
        // Arrange
        var project = projectRepository.getProjectById(1);

        // act and assert
        assertThat(project).isNotNull();
        assertThat(project.getTitle().equals("projectTestTitle"));
        assertThat(project.getDescription().equals("projectTestDescription"));
        assertThat(project.getDeadline()).isEqualTo(LocalDate.of(2025, 1, 1));
        assertThat(project.getProjectId() == 1);



    }

    @Test
    void getAllProjects() {

        // Arrange
        var projects = projectRepository.getUsersProjectsByUserId(1);

        // Act and assert
        for (Project project : projects) {
            assertThat(projects).isNotNull();
            assertThat(project.getTitle().equals("projectTestTitle"));
            assertThat(project.getDescription().equals("projectTestDescription"));
            assertThat(project.getDeadline()).isEqualTo(LocalDate.of(2025, 1, 1));
            assertThat(project.getProjectId() == 1);
        }

    }
}

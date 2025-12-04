package com.hauxy.projectwizard;

import com.hauxy.projectwizard.model.Project;
import com.hauxy.projectwizard.model.User;
import com.hauxy.projectwizard.repository.DAO.ProjectDAO;
import com.hauxy.projectwizard.service.ProjectService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@SpringBootTest
@ActiveProfiles("test")
@Sql(scripts = "/h2init.sql", executionPhase = BEFORE_TEST_METHOD)
class ProjectControllerIntegrationTest {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ProjectDAO projectDAO;

    @Test
    void removeMember_removesCorrectlyFromDatabase() {
        // Før: der ER en relation i users_to_project
        assertThat(projectService.getProjectMembers(1).size()).isEqualTo(1);

        // Brugeren har id = 1 (fra din h2init.sql)
        projectService.removeMember(1, 1);

        // Efter: ingen relation
        assertThat(projectService.getProjectMembers(1).size()).isEqualTo(0);
    }

    @Test
    void getProjectAndMembers_returnsCorrectData() {
        Project project = projectService.getProjectById(1);
        List<User> members = projectService.getProjectMembers(1);

        assertThat(project.getTitle()).isEqualTo("projectTestTitle");
        assertThat(members.size()).isEqualTo(1);
    }

}
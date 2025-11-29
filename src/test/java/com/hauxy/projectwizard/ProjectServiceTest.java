package com.hauxy.projectwizard;

import com.hauxy.projectwizard.model.Project;
import com.hauxy.projectwizard.repository.ProjectRepository;
import com.hauxy.projectwizard.service.ProjectService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class ProjectServiceTest {

    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private ProjectService projectService;

    @Test
    void createProjectWithCreator_ShouldCreateProjectAndAddUser() {
        Project project = new Project();
        int userId = 10;
        int createdProjectId = 123;

        when(projectRepository.getLastCreatedProjectId()).thenReturn(createdProjectId);

        // Act
        projectService.createProjectWithCreator(project, userId);

        // Assert
        InOrder order = inOrder(projectRepository);

        order.verify(projectRepository).createNewProject(project);
        order.verify(projectRepository).getLastCreatedProjectId();
        order.verify(projectRepository).addUserToProject(userId, createdProjectId);
    }
}


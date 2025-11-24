package com.hauxy.projectwizard.repository;

import com.hauxy.projectwizard.model.Project;
import com.hauxy.projectwizard.repository.DAO.ProjectDAO;
import com.hauxy.projectwizard.repository.DAO.UserDAO;
import org.springframework.stereotype.Repository;

@Repository
public class ProjectRepository {
    private final ProjectDAO projectDAO;

    public ProjectRepository(ProjectDAO projectDAO) {
        this.projectDAO = projectDAO;
    }

    public int createNewProject(Project newProject) {
        return projectDAO.createNewProject(newProject);
    }
}

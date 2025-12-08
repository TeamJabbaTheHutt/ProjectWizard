package com.hauxy.projectwizard.repository;

import com.hauxy.projectwizard.model.Project;
import com.hauxy.projectwizard.model.User;
import com.hauxy.projectwizard.repository.DAO.ProjectDAO;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class ProjectRepository {
    private final ProjectDAO projectDAO;

    public ProjectRepository(ProjectDAO projectDAO) {
        this.projectDAO = projectDAO;
    }

    public int createNewProject(Project newProject) {
        return projectDAO.createNewProject(newProject);
    }

    public int getLastCreatedProjectId() {
        return projectDAO.getLastCreatedProjectId();
    }

    public int addUserToProject(int userId, int projectId) {
        return projectDAO.addUserToProject(userId, projectId);
    }

    public List<Project> getUsersProjectsByUserId(int userId) {
        return projectDAO.getUsersProjectsByUserId(userId);
    }

    public Project getProjectById(int projectId) {
        return projectDAO.getProjectById(projectId);
    }

    public List<Project> getAllProjects() {
        return projectDAO.getAllProjects();
    }

    public void updateProject(int id, String title, String description, String deadline) {
        projectDAO.updateProject(id, title, description, deadline);
    }

    public List<User> getProjectMembers(int projectId) {
        return projectDAO.getProjectMembers(projectId);
    }

    public void removeMember(int projectId, int memberId) {
        projectDAO.removeMemberFromProject(projectId, memberId);
    }

    public boolean deleteProject(Project project) {
        return projectDAO.deleteProject(project);
    }

}

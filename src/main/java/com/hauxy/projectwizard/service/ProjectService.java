package com.hauxy.projectwizard.service;

import com.hauxy.projectwizard.model.Project;
import com.hauxy.projectwizard.model.User;
import com.hauxy.projectwizard.repository.ProjectRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public String createNewProject(Project project) {
        if (projectRepository.createNewProject(project) == 1) {
            return "Success";
        }
        return "Failure";
    }

    @Transactional
    public void createProjectWithCreator(Project project, int userId) {

        // Create new project
        projectRepository.createNewProject(project);

        // Fetch project ID only IF project was created
        int projectId = projectRepository.getLastCreatedProjectId();

        // Add user to the list of members on the project
        projectRepository.addUserToProject(userId, projectId);
    }

    public List<Project> getUsersProjectsByUserId(int userId) {
        return projectRepository.getUsersProjectsByUserId(userId);
    }

    public void updateProject(int id, String title, String description, String deadline) {
        projectRepository.updateProject(id, title, description, deadline);
    }

    public Project getProjectById(int id) {
        return projectRepository.getProjectById(id);
    }

    public List<User> getProjectMembers(int projectId) {
        return projectRepository.getProjectMembers(projectId);
    }

    public void removeMember(int projectId, int memberId) {
        projectRepository.removeMember(projectId, memberId);
    }

    public void addMember(int projectId, int userId) {
        projectRepository.addUserToProject(projectId, userId);
    }

}

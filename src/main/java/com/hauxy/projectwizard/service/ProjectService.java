package com.hauxy.projectwizard.service;

import com.hauxy.projectwizard.model.Project;
import com.hauxy.projectwizard.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    // createNewProject() { // Project newProject = repo.createNewProject()} (OnSuccess = newProject.getProjectId))


    public List<Project> getUsersProjectsByUserId(int userId) {
        return projectRepository.getUsersProjectsByUserId(userId);
    }
}

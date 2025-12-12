package com.hauxy.projectwizard.service;

import com.hauxy.projectwizard.exceptions.DatabaseOperationException;
import com.hauxy.projectwizard.model.*;
import com.hauxy.projectwizard.repository.ProjectRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final SubprojectService subprojectService;
    private final TaskService taskService;
    private final SubtaskService subtaskService;


    public ProjectService(ProjectRepository projectRepository,  SubprojectService subprojectService, TaskService taskService, SubtaskService subtaskService) {
        this.projectRepository = projectRepository;
        this.subprojectService = subprojectService;
        this.taskService = taskService;
        this.subtaskService = subtaskService;
    }

    @Transactional
    public void createProjectWithCreator(Project project, int userId) {
        projectRepository.createNewProject(project);
        int projectId = projectRepository.getLastCreatedProjectId();
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

    public void addUserToProject(int userId, int projectId) {
        projectRepository.addUserToProject(userId, projectId);
    }

    public List<Subproject> getAllSubProjectsByProjectId(int projectId) {
        return subprojectService.getAllSubProjectsByProjectId(projectId);
    }

    public List<Task> getAllTasksByProjectId(int projectId) {
        List<Subproject> subprojects = getAllSubProjectsByProjectId(projectId);
        List<Task> tasks = new ArrayList<>();

        for (Subproject subproject : subprojects) {
            tasks.addAll(taskService.getAllTasksBySubprojectId(subproject.getSubProjectId()));
        }

        return tasks;
    }

    public List<Subtask> getAllSubTasksByProjectId(int projectId) {
        List<Task> tasks = getAllTasksByProjectId(projectId);
        List<Subtask> subtasks = new ArrayList<>();
        for (Task task : tasks) {
            subtasks.addAll(subtaskService.getAllSubTasksByTaskId(task.getTaskId()));
        }
        return subtasks;
    }


    public boolean deleteProject(Project project) {
        return projectRepository.deleteProject(project);
    }
}

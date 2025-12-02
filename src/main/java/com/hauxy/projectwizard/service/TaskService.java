package com.hauxy.projectwizard.service;

import com.hauxy.projectwizard.model.Task;
import com.hauxy.projectwizard.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }


    public List<Task> getAllTasksByProjectId(Integer projectId) {
        return taskRepository.getAllTasksByProjectId(projectId);
    }

    public int createTask(String title, String description, int projectId, Integer parentTaskId) {
        Task task = new Task();
        task.setTitle(title);
        task.setDescription(description);
        task.setProjectId(projectId);

        return taskRepository.createTask(task, parentTaskId);
    }


}

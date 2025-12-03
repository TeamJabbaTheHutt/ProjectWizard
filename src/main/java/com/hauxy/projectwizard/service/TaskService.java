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


    public List<Task> getAllTasksBySubprojectId(Integer subprojectId) {
        return taskRepository.getTasksByProjectId(subprojectId);
    }

    public int createTask(String title, String description, int projectId, Integer parentTaskId) {
        Task task = new Task();
        task.setTitle(title);
        task.setDescription(description);

        return taskRepository.createTask(task, parentTaskId);
    }

    public Task getTaskById(Integer taskId, Integer subprojectId) {
        List<Task> tasks = getAllTasksBySubprojectId(subprojectId);
        for (Task task : tasks) {
            if (task.getTaskId() == taskId) {
                return task;
            }
        }
        return null;

    }


}

package com.hauxy.projectwizard.service;

import com.hauxy.projectwizard.model.Status;
import com.hauxy.projectwizard.model.Task;
import com.hauxy.projectwizard.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserService userService;

    public TaskService(TaskRepository taskRepository, UserService userService) {
        this.taskRepository = taskRepository;
        this.userService = userService;
    }

    public List<Task> getAllTasksBySubprojectId(Integer subprojectId) {
        List<Task> tasks = taskRepository.getTasksByProjectId(subprojectId);
        for (Task task : tasks) {
            task.setAssignee(userService.getUserById(task.getAssigneeId()));
        }
        return tasks;
    }

    public int createTask(String title, String description, Integer parentTaskId, LocalDate deadline) {
        Task task = new Task();
        task.setTitle(title);
        task.setDescription(description);
        task.setDeadline(deadline);
        task.setStatus(Status.NoStatus);
        return taskRepository.createTask(task, parentTaskId);
    }

    public Task getTaskById(int taskId) {
        Task task = taskRepository.getTaskById(taskId);
        task.setAssignee(userService.getUserById(task.getAssigneeId()));
        return task;
    }

    public boolean updateTask(Task task) {
        return taskRepository.updateTask(task);
    }

    public boolean addUserToTask(String email, int taskId) {
        if (userService.getUserByEmail(email) == null) {
            return false;
        } else {
            taskRepository.setAssignee(userService.getUserByEmail(email).getUserId(), taskId);
            return true;
        }
    }

    public boolean removeUserFromTask(int taskId) {
        return taskRepository.removeAssignee(taskId);
    }

    public boolean deleteTask(Task task) {
        return taskRepository.deleteTask(task);
    }
}

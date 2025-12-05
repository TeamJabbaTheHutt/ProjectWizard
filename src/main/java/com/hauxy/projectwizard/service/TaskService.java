package com.hauxy.projectwizard.service;

import com.hauxy.projectwizard.model.Status;
import com.hauxy.projectwizard.model.Task;
import com.hauxy.projectwizard.model.User;
import com.hauxy.projectwizard.repository.TaskRepository;
import com.hauxy.projectwizard.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public List<Task> getAllTasksBySubprojectId(Integer subprojectId) {
        List<Task> tasks = taskRepository.getTasksByProjectId(subprojectId);
        for (Task task : tasks) {
            task.setAssignee(userRepository.getUserById(task.getAssigneeId()));
        }
        return tasks;
    }

    public int createTask(String title, String description, Integer parentTaskId, LocalDate deadline) {
        Task task = new Task();
        task.setTitle(title);
        task.setDescription(description);
        task.setDeadline(deadline);
        task.setStatus(Status.NoStatus); // default status
        return taskRepository.createTask(task, parentTaskId);
    }

    public Task getTaskById(int taskId, int subprojectId) {
        List<Task> tasks = getAllTasksBySubprojectId(subprojectId);
        for (Task task : tasks) {
            if (task.getTaskId() == taskId) return task;
        }
        return null;
    }

    public void updateTask(int taskId, String title, String description, LocalDate deadline) {
        Task task = taskRepository.getTaskById(taskId);
        if (task == null) return;
        task.setTitle(title);
        task.setDescription(description);
        task.setDeadline(deadline);
        taskRepository.updateTask(task);
    }

    public boolean addUserToTask(int taskId, String email) {
        User user = userRepository.getUserByEmail(email);
        if (user == null) return false;
        Task task = taskRepository.getTaskById(taskId);
        task.setAssignee(user);
        taskRepository.updateTask(task);
        return true;
    }

    public boolean removeUserFromTask(int taskId, String email) {
        Task task = taskRepository.getTaskById(taskId);
        if (task == null || task.getAssignee() == null) return false;
        if (!task.getAssignee().getEmail().equals(email)) return false;
        task.setAssignee(null);
        taskRepository.updateTask(task);
        return true;
    }
}

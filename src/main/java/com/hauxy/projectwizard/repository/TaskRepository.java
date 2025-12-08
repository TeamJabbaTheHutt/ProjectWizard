package com.hauxy.projectwizard.repository;

import com.hauxy.projectwizard.model.Task;
import com.hauxy.projectwizard.repository.DAO.TaskDAO;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class TaskRepository {
    private final TaskDAO taskDAO;

    public TaskRepository(TaskDAO taskDAO) {
        this.taskDAO = taskDAO;
    }

    public int createTask(Task task, Integer parentTaskId) {
        return taskDAO.insertTask(task, parentTaskId);
    }

    public List<Task> getTasksByProjectId(int subprojectId) {
        return taskDAO.getTasksBySubprojectId(subprojectId);
    }

    public Task getTaskById(int taskId) {
        return taskDAO.getTaskById(taskId);
    }

    public boolean updateTask(Task task) {
        return taskDAO.updateTask(task);
    }

    public boolean setAssignee(int userId, int taskId) {
        return taskDAO.setAssignee(userId, taskId);
    }
    public boolean removeAssignee(int taskId) {
        return taskDAO.removeAssignee(taskId);
    }

    public boolean deleteTask(Task task) {
        return taskDAO.deleteTask(task);
    }

}

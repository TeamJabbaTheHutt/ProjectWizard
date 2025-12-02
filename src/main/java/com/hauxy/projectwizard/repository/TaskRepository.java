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



    public List<Task> getAllTasksByProjectId(int projectId) {
        return taskDAO.getTasksByProjectId(projectId);
    }

    public int createTask(Task task, Integer parentTaskId) {
        return taskDAO.insertTask(task, parentTaskId);
    }

    public Task getTaskById(int taskId) {
        return taskDAO.getTaskById(taskId);
    }


}

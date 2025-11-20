package com.hauxy.projectwizard.repository;

import com.hauxy.projectwizard.repository.DAO.TaskDAO;
import org.springframework.stereotype.Repository;

@Repository
public class TaskRepository {
    private final TaskDAO taskDAO;

    public TaskRepository(TaskDAO taskDAO) {
        this.taskDAO = taskDAO;
    }
}

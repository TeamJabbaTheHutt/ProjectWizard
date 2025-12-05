package com.hauxy.projectwizard.repository;

import com.hauxy.projectwizard.model.Subtask;
import com.hauxy.projectwizard.model.Task;
import com.hauxy.projectwizard.repository.DAO.SubtaskDAO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SubtaskRepository {
    private final SubtaskDAO subtaskDAO;

    public SubtaskRepository(SubtaskDAO subtaskDAO) {
        this.subtaskDAO = subtaskDAO;
    }



    public List<Subtask> getAllSubtasksByProjectId(int taskId) {

        return subtaskDAO.getAllSubtasks(taskId);
    }

    public void updateTask(Subtask subtask) {
        subtaskDAO.updateTask(subtask);
    }

    public Subtask getSubTaskById(int subTaskId) {
        return subtaskDAO.getSubTaskById(subTaskId);
    }

}

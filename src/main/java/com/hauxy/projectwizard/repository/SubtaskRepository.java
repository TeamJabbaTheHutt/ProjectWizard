package com.hauxy.projectwizard.repository;

import com.hauxy.projectwizard.model.Subtask;
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

    public boolean updateTask(Subtask subtask) {
        return subtaskDAO.updateSubtask(subtask);
    }

    public int createSubtask(Subtask subtask) {
        return subtaskDAO.createSubtask(subtask);
    }

    public Subtask getSubtaskById(int taskId) {
        return subtaskDAO.getSubtaskById(taskId);
    }

    public boolean setAssignee(int userId, int subtaskId) {
        return subtaskDAO.setAssignee(userId, subtaskId);
    }

    public boolean removeAssignee(int subtaskId) {
        return subtaskDAO.removeAssignee(subtaskId);
    }

    public boolean deleteSubtask(Subtask subtask) {
        return subtaskDAO.deleteSubtask(subtask);
    }
}

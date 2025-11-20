package com.hauxy.projectwizard.repository;

import com.hauxy.projectwizard.model.Subtask;
import com.hauxy.projectwizard.repository.DAO.SubtaskDAO;
import org.springframework.stereotype.Repository;

@Repository
public class SubtaskRepository {
    private final SubtaskDAO subtaskDAO;

    public SubtaskRepository(SubtaskDAO subtaskDAO) {
        this.subtaskDAO = subtaskDAO;
    }
}

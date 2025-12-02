package com.hauxy.projectwizard.service;

import com.hauxy.projectwizard.model.Subtask;
import com.hauxy.projectwizard.repository.SubtaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubtaskService {
    private final SubtaskRepository subtaskRepository;

    public SubtaskService(SubtaskRepository subtaskRepository) {
        this.subtaskRepository = subtaskRepository;
    }

    public List<Subtask> getAllSubTasksByProjectId(int projectId) {
        return subtaskRepository.getAllSubtasksByProjectId(projectId);
    }



    public int updateSubtask(Subtask subtask) {
        return subtaskRepository.updateSubtask(subtask);
    }

}

package com.hauxy.projectwizard.service;

import com.hauxy.projectwizard.model.Subtask;
import com.hauxy.projectwizard.model.Task;
import com.hauxy.projectwizard.repository.SubtaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubtaskService {
    private final SubtaskRepository subtaskRepository;

    public SubtaskService(SubtaskRepository subtaskRepository) {
        this.subtaskRepository = subtaskRepository;
    }



    public List<Subtask> getAllSubTasksByTaskId(int taskId) {
        return subtaskRepository.getAllSubtasksByProjectId(taskId);
    }

    public Subtask getSubtaskById(int subtaskId, int taskId) {
        List<Subtask> subTasks = getAllSubTasksByTaskId(taskId);
        for (Subtask subtask : subTasks) {
            if (subtask.getTaskId() == taskId) {
                return subtask;
            }
        }
        return null;
    }
}

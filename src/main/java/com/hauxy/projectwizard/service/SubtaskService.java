package com.hauxy.projectwizard.service;

import com.hauxy.projectwizard.model.Subtask;
import com.hauxy.projectwizard.repository.SubtaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubtaskService {
    private final SubtaskRepository subtaskRepository;
    private final UserService userService;

    public SubtaskService(SubtaskRepository subtaskRepository, UserService userService) {
        this.subtaskRepository = subtaskRepository;
        this.userService = userService;
    }

    public boolean updateSubtask(Subtask subtask) {

        if (subtask.getEstimate() == null) {
            subtask.setEstimate(0.0);
        }

        if (subtask.getActualTime() == null) {
            subtask.setActualTime(0.0);
        }
        return subtaskRepository.updateTask(subtask);
    }

    public List<Subtask> getAllSubTasksByTaskId(int taskId) {
        List<Subtask> subtasks = subtaskRepository.getAllSubtasksByProjectId(taskId);

        for (Subtask subtask : subtasks) {
            if (subtask.getAssigneeId() > 0) {
                subtask.setAssignee(userService.getUserById(subtask.getAssigneeId()));
            }
        }

        return subtasks;
    }

    public int createSubtask(Subtask subtask) {
        return subtaskRepository.createSubtask(subtask);
    }

    public Subtask getTaskById(int subtaskId) {
        Subtask subtask = subtaskRepository.getSubtaskById(subtaskId);
        subtask.setAssignee(userService.getUserById(subtask.getAssigneeId()));
        return subtask;
    }


    public boolean addUserToSubtask(String email, int subtaskId) {
        if (userService.getUserByEmail(email) == null) {
            return false;
        } else {
            subtaskRepository.setAssignee(userService.getUserByEmail(email).getUserId(), subtaskId);
            return true;
        }
    }


    public boolean removeUserFromTask(int subtaskId) {

        subtaskRepository.removeAssignee(subtaskId);

        return false;
    }

    public boolean deleteSubtask(Subtask subtask) {
        return subtaskRepository.deleteSubtask(subtask);
    }

}

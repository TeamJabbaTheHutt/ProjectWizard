package com.hauxy.projectwizard.service;

import com.hauxy.projectwizard.model.Subtask;
import com.hauxy.projectwizard.model.Task;
import com.hauxy.projectwizard.repository.SubtaskRepository;
import com.hauxy.projectwizard.repository.UserRepository;
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
        return subtaskRepository.updateTask(subtask);
    }

    public List<Subtask> getAllSubTasksByTaskId(int taskId) {
        return subtaskRepository.getAllSubtasksByProjectId(taskId);
    }

    public Subtask getSubtaskById(int subtaskId, int taskId) {
        List<Subtask> subTasks = getAllSubTasksByTaskId(taskId);
        for (Subtask subtask : subTasks) {
            if (subtask.getSubtaskId() == taskId) {
                return subtask;
            }
        }
        return null;
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

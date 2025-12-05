package com.hauxy.projectwizard.service;

import com.hauxy.projectwizard.model.Subtask;
import com.hauxy.projectwizard.model.Task;
import com.hauxy.projectwizard.model.User;
import com.hauxy.projectwizard.repository.SubtaskRepository;
import com.hauxy.projectwizard.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class SubtaskService {
    private final SubtaskRepository subtaskRepository;
    private final UserRepository userRepository;

    public SubtaskService(SubtaskRepository subtaskRepository, UserRepository userRepository) {
        this.subtaskRepository = subtaskRepository;
        this.userRepository = userRepository;
    }



    public List<Subtask> getAllSubTasksByTaskId(int taskId) {
        List<Subtask> subtasks = subtaskRepository.getAllSubtasksByProjectId(taskId);
        for(Subtask subtask : subtasks) {
            subtask.setAssignee(userRepository.getUserById(subtask.getAssigneeId()));
        }
        return subtasks;
    }

    public Subtask getSubtaskById(int subtaskId, int parentId) {
        List<Subtask> subTasks = getAllSubTasksByTaskId(parentId);
        for (Subtask subtask : subTasks) {
            if (subtask.getTaskId() == subtaskId) {
                return subtask;
            }
        }
        return null;
    }

    public void updateSubTask(int subTaskId, String title, String description) {
        Subtask subTask = subtaskRepository.getSubTaskById(subTaskId);
        if (subTask == null) return;
        subTask.setTitle(title);
        subTask.setDescription(description);
        subtaskRepository.updateTask(subTask);
    }

    public boolean addUserToSubTask(int subtaskId, String email) {
        User user = userRepository.getUserByEmail(email);
        System.out.println(user.getEmail());
        if (user == null) return false;
        Subtask subtask = subtaskRepository.getSubTaskById(subtaskId);
        subtask.setAssignee(user);
        subtaskRepository.updateTask(subtask);
        return true;
    }
    public boolean removeUserFromSubTask(int taskId) {
        Subtask subtask = subtaskRepository.getSubTaskById(taskId);
        subtask.setAssignee(null);
        subtaskRepository.updateTask(subtask);
        return true;
    }

}

package com.hauxy.projectwizard.model;

import java.time.LocalDate;
import java.util.List;

public class Task {
    private int taskId;
    private String title;
    private String description;
    private User assignee;
    private Status status;
    private Double estimate;
    private Double actualTime;
    private int parentId;
    private LocalDate deadline;
    private List<Subtask> subtasks;

    private int assigneeId;

    public Task() {}

    public Task(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public int getParentId() {
        return parentId;
    }
    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public int getAssigneeId() {
        return assigneeId;
    }

    public void setAssigneeId(int assigneeId) {
        this.assigneeId = assigneeId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public User getAssignee() {
        return assignee;
    }

    public void setAssignee(User assignee) {
        this.assignee = assignee;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Double getEstimate() {
        return estimate;
    }

    public void setEstimate(Double estimate) {
        this.estimate = estimate;
    }

    public Double getActualTime() {
        return actualTime;
    }

    public void setActualTime(Double actualTime) {
        this.actualTime = actualTime;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Subtask> getSubtasks() {
        return subtasks;
    }

    public void setSubtasks(List<Subtask> subtasks) {
        this.subtasks = subtasks;
    }
}

package com.hauxy.projectwizard.model;

public class Task {
    private int taskId;
    private String title;
    private String description;
    private User assignee;
    private Status status;
    private double estimate;
    private double actualTime;
    private int projectId;

    public Task() {}

    public Task(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
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

    public double getEstimate() {
        return estimate;
    }

    public void setEstimate(double estimate) {
        this.estimate = estimate;
    }

    public double getActualTime() {
        return actualTime;
    }

    public void setActualTime(double actualTime) {
        this.actualTime = actualTime;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

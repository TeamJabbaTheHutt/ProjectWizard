package com.hauxy.projectwizard.model;

import java.time.LocalDate;
import java.util.List;

public class Project {
    private int projectId;
    private final String title;
    private final String description;
    private final LocalDate deadline;
    private List<Task> tasks;
    private List<User> members;

    public Project(String title, String description, LocalDate deadline) {
        this.title = title;
        this.description = description;
        this.deadline = deadline;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public List<User> getMembers() {
        return members;
    }

    public void setMembers(List<User> members) {
        this.members = members;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public LocalDate getDeadline() {
        return deadline;
    }
}

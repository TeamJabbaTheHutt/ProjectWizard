package com.hauxy.projectwizard.model;

import java.time.LocalDate;

public class Subproject {
    private int subProjectId;
    private int parentId;
    private final String title;
    private final String description;
    private LocalDate deadline;

    public Subproject(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public int getSubProjectId() {
        return subProjectId;
    }

    public void setSubProjectId(int subProjectId) {
        this.subProjectId = subProjectId;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }
}

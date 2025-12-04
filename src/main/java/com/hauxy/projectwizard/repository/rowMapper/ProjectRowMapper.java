package com.hauxy.projectwizard.repository.rowMapper;

import com.hauxy.projectwizard.model.Project;

import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProjectRowMapper implements RowMapper<Project> {

    @Override
    public Project mapRow(ResultSet rs, int rowNum) throws SQLException { // for merge
        Project project = new Project();
        project.setProjectId(rs.getInt("project_id"));
        project.setTitle(rs.getString("title"));
        project.setDescription(rs.getString("project_description"));
        java.sql.Date createdAtDate = rs.getDate("created_at");
        if (createdAtDate != null) {
            project.setCreatedAt(createdAtDate.toLocalDate());
        }

        java.sql.Date deadlineDate = rs.getDate("deadline");
        if (deadlineDate != null) {
            project.setDeadline(deadlineDate.toLocalDate());
        }

        return project;
    }
}

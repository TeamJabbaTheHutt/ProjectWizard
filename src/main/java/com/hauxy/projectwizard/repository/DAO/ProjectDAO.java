package com.hauxy.projectwizard.repository.DAO;

import com.hauxy.projectwizard.model.Project;
import com.hauxy.projectwizard.repository.rowMapper.ProjectRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class ProjectDAO {

    private final JdbcTemplate jdbc;
    private final ProjectRowMapper projectRowMapper = new ProjectRowMapper();

    public ProjectDAO(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public int createNewProject(Project newProject) {
        String sql = "INSERT INTO projects (title, description, deadline) VALUES (?, ?, ?)";
        return jdbc.update(sql, newProject.getTitle(), newProject.getDescription(), newProject.getDeadline());
    }

    // assignMemberToProject(String email) { "INSERT INTO users_to_projects (?, ?)"} (userId (for users.. if user.getEmail = bla, user.userID = ..), projectId, getprojectID )
}

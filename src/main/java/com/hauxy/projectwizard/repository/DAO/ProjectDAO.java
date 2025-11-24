package com.hauxy.projectwizard.repository.DAO;

import com.hauxy.projectwizard.model.Project;
import com.hauxy.projectwizard.repository.rowMapper.ProjectRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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

    //getUserPerProject from user id

    // for at kunne få all usererens projects for user id, skal jeg først finde all projekter der tilhører useren ud fra userens ID

    public List<Project> getUsersProjectsByUserId(int userId) {
        List<Integer> usersProjectIds = new ArrayList<>();
        List<Project> usersProjects = new ArrayList<>();
        String sqlQueryForFetchingProjectId = "SELECT project_id FROM users_to_project WHERE user_id = ?";
        usersProjectIds = jdbc.queryForList(sqlQueryForFetchingProjectId, Integer.class, userId);

        for (Integer projectId : usersProjectIds) {
            usersProjects.add(getProjectById(projectId));
        }

        return usersProjects;
    }

    public Project getProjectById(int projectId) {
        String sql = "SELECT * FROM projects WHERE project_id = ?";
        return jdbc.queryForObject(sql, projectRowMapper, projectId);
    }

    //get projects
    public List<Project> getAllProjects() {
        String sql = "SELECT * FROM projects";
        return jdbc.query(sql, projectRowMapper);
    }

}

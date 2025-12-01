package com.hauxy.projectwizard.repository.DAO;

import com.hauxy.projectwizard.model.Project;
import com.hauxy.projectwizard.model.User;
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

    @Transactional
    public int createNewProject(Project newProject) {
        String sql = "INSERT INTO project (title, project_description, deadline) VALUES (?, ?, ?)";
        return jdbc.update(sql, newProject.getTitle(), newProject.getDescription(), newProject.getDeadline());
    }

    public int getLastCreatedProjectId() {
        String sql = "SELECT MAX(project_id) FROM project";
        return jdbc.queryForObject(sql, Integer.class);
    }

    public int addUserToProject(int userId, int projectId) {
        String sql = "INSERT INTO users_to_project (user_id, project_id) VALUES (?, ?)";
        return jdbc.update(sql, userId, projectId);
    }

    //getUserPerProject from user id

    // for at kunne få all usererens projects for user id, skal jeg først finde all projekter der tilhører useren ud fra userens ID

    public List<Project> getUsersProjectsByUserId(int userId) {
        List<Integer> usersProjectIds;
        List<Project> usersProjects = new ArrayList<>();
        String sqlQueryForFetchingProjectId = "SELECT project_id FROM users_to_project WHERE user_id = ?";
        usersProjectIds = jdbc.queryForList(sqlQueryForFetchingProjectId, Integer.class, userId);

        for (Integer projectId : usersProjectIds) {
            usersProjects.add(getProjectById(projectId));
        }

        return usersProjects;
    }

    public Project getProjectById(int projectId) {
        String sql = "SELECT * FROM project WHERE project_id = ?";
        return jdbc.queryForObject(sql, projectRowMapper, projectId);
    }

    //get projects
    public List<Project> getAllProjects() {
        String sql = "SELECT * FROM project";
        return jdbc.query(sql, projectRowMapper);
    }

    //Update
    public void updateProject(int projectId, String title, String description, String deadline) {
        String sql = """
            UPDATE project
            SET title = ?, project_description = ?, deadline = ?
            WHERE project_id = ?
        """;

        jdbc.update(sql, title, description, java.sql.Date.valueOf(deadline), projectId);
    }

    public List<User> getProjectMembers(int projectId) {

        String sql = """
            SELECT u.user_id, u.username, u.email, u.user_password
            FROM users u
            JOIN users_to_project up ON u.user_id = up.user_id
            WHERE up.project_id = ?
        """;

        return jdbc.query(sql, (rs, rowNum) -> {
            User user = new User();
            user.setUserId(rs.getInt("user_id"));
            user.setUsername(rs.getString("username"));
            user.setEmail(rs.getString("email"));
            user.setPassword(rs.getString("user_password"));
            return user;
        }, projectId);
    }

    public void removeMemberFromProject(int projectId, int userId) {
        String sql = "DELETE FROM users_to_project WHERE project_id = ? AND user_id = ?";
        jdbc.update(sql, projectId, userId);
    }

}

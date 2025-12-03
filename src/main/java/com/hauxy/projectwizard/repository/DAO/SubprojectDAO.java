package com.hauxy.projectwizard.repository.DAO;

import com.hauxy.projectwizard.model.Subproject;
import com.hauxy.projectwizard.model.Subtask;
import com.hauxy.projectwizard.model.Task;
import com.hauxy.projectwizard.repository.rowMapper.SubprojectRowMapper;
import com.hauxy.projectwizard.repository.rowMapper.TaskRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class SubprojectDAO {


    @Autowired
    private final JdbcTemplate jdbc;
    private final SubprojectRowMapper subprojectRowMapper = new SubprojectRowMapper();

    public SubprojectDAO(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public List<Subproject> getAllSubProjectsByProjectId(int projectId) {
        String sql = "SELECT * FROM sub_project WHERE parent_id = ?";
        List<Subproject> subproject = jdbc.query(sql, subprojectRowMapper, projectId);
        return subproject;
    }
    public int createSubproject(Subproject subproject) {
        String sql = "INSERT INTO sub_project (parent_id, subproject_title, sub_project_description, created_at, deadline) VALUES (?, ?, ?, ?, ?)";
        return jdbc.update(sql, subproject.getParentId(),subproject.getTitle(), subproject.getDescription(), LocalDate.now(), subproject.getDeadline());
    }



}

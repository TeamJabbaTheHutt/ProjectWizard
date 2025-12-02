package com.hauxy.projectwizard.repository.DAO;

import com.hauxy.projectwizard.model.Subtask;
import com.hauxy.projectwizard.model.Task;
import com.hauxy.projectwizard.repository.rowMapper.SubtaskRowMapper;
import com.hauxy.projectwizard.repository.rowMapper.TaskRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SubtaskDAO {


    @Autowired
    private final JdbcTemplate jdbc;
    private final SubtaskRowMapper subtaskRowMapper = new SubtaskRowMapper();


    public SubtaskDAO(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }


    public List<Subtask> getTasksByProjectId(int projectId) {
        String sql = "SELECT * FROM subtask WHERE project_id = ?";
        List<Subtask> subtasks = jdbc.query(sql, subtaskRowMapper, projectId);
        return subtasks;
    }

    public int updateSubtask(Subtask subtask) {
        String sql = "UPDATE subtask SET title = ?, subtask_description = ? WHERE subtask_id = ?";
        return jdbc.update(sql, subtask.getTitle(), subtask.getDescription(), subtask.getTaskId());
    }

}

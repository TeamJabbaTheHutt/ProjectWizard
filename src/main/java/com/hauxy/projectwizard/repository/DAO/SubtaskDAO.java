package com.hauxy.projectwizard.repository.DAO;

import com.hauxy.projectwizard.model.Subproject;
import com.hauxy.projectwizard.model.Subtask;
import com.hauxy.projectwizard.model.Task;
import com.hauxy.projectwizard.repository.rowMapper.SubtaskRowMapper;
import com.hauxy.projectwizard.repository.rowMapper.TaskRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class SubtaskDAO {


    @Autowired
    private final JdbcTemplate jdbc;
    private final SubtaskRowMapper subtaskRowMapper = new SubtaskRowMapper();


    public SubtaskDAO(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public List<Subtask> getAllSubtasks(int taskId) {
        String sql = "SELECT * FROM subtask WHERE parent_id = ?";
        List<Subtask> subtasks = jdbc.query(sql, subtaskRowMapper, taskId);
        return subtasks;
    }



    public int createSubtask(Subtask subtask) {
        String sql = "INSERT INTO subtask (title, subtask_description, parent_id) VALUES (?, ?, ?)";
        return jdbc.update(sql, subtask.getTitle(), subtask.getDescription(), subtask.getParentId());
    }

    public Subtask getSubtaskById(int subtaskId) {
        String sql = "SELECT * FROM subtask WHERE subtask_id = ?";
        return jdbc.queryForObject(sql, subtaskRowMapper, subtaskId);
    }

    public boolean updateSubtask(Subtask subtask) {
        try {
            String sql = "UPDATE subtask SET title = ?, subtask_description = ?, subtask_status = ?, estimated_time = ?, actual_time = ? WHERE subtask_id = ?";

            jdbc.update(sql,
                    subtask.getTitle(),
                    subtask.getDescription(),
                    subtask.getStatus() != null ? subtask.getStatus().name() : "NoStatus",
                    subtask.getEstimate(),
                    subtask.getActualTime(),
                    subtask.getSubtaskId()
            );
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public boolean setAssignee(int userId, int subtaskId) {
        try {
            String sql = "UPDATE subtask SET assignee_id = ? WHERE subtask_id = ?";
            jdbc.update(sql, userId, subtaskId);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean removeAssignee(int subtaskId) {
        try {
            String sql = "UPDATE subtask SET assignee_id = ? WHERE subtask_id = ?";
            jdbc.update(sql,null, subtaskId);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}

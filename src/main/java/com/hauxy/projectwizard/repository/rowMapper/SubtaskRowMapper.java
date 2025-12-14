package com.hauxy.projectwizard.repository.rowMapper;

import com.hauxy.projectwizard.model.Status;
import com.hauxy.projectwizard.model.Subtask;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SubtaskRowMapper implements RowMapper<Subtask> {

    @Override
    public Subtask mapRow(ResultSet rs, int rowNum) throws SQLException {
        Subtask subtask = new Subtask();
        subtask.setSubtaskId(rs.getInt("subtask_id"));
        subtask.setTitle(rs.getString("title"));
        subtask.setDescription(rs.getString("subtask_description"));
        subtask.setStatus(Status.valueOf(rs.getString("subtask_status")));
        subtask.setParentId(rs.getInt("parent_id"));
        subtask.setEstimate(rs.getDouble("estimated_time"));
        subtask.setAssigneeId(rs.getInt("assignee_id"));
        subtask.setActualTime(rs.getDouble("actual_time"));
        return subtask;
    }
}

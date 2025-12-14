package com.hauxy.projectwizard.repository.rowMapper;

import com.hauxy.projectwizard.model.Status;
import com.hauxy.projectwizard.model.Task;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TaskRowMapper implements RowMapper<Task> {

    @Override
    public Task mapRow(ResultSet rs, int rowNum) throws SQLException {
        Task task = new Task();
        task.setTaskId(rs.getInt("task_id"));
        task.setTitle(rs.getString("title"));
        task.setParentId(rs.getInt("parent_id"));
        task.setAssigneeId(rs.getInt("assignee_id"));
        task.setEstimate(rs.getDouble("estimated_time"));
        task.setActualTime(rs.getDouble("actual_time"));
        task.setDescription(rs.getString("task_description"));
        task.setStatus(Status.valueOf(rs.getString("task_status")));

        return task;
    }
}

package com.hauxy.projectwizard.repository.DAO;

import com.hauxy.projectwizard.model.Task;
import com.hauxy.projectwizard.repository.rowMapper.TaskRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TaskDAO {

    private final JdbcTemplate jdbc;
    private final TaskRowMapper taskRowMapper = new TaskRowMapper();

    public TaskDAO(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public List<Task> getTasksBySubprojectId(int subprojectId) {
        String sql = "SELECT * FROM task WHERE parent_id = ?";
        return jdbc.query(sql, taskRowMapper, subprojectId);
    }

    public int insertTask(Task task, int parentId) {
        String sql = "INSERT INTO task (title, task_description, parent_id, task_status, estimated_time, assignee_id, actual_time) VALUES (?, ?, ?, ?, ?, ?, ?)";
        Integer assigneeId = task.getAssignee() != null ? task.getAssignee().getUserId() : null;
        return jdbc.update(sql,
                task.getTitle(),
                task.getDescription(),
                parentId,
                task.getStatus() != null ? task.getStatus().name() : "NoStatus",
                task.getEstimate(),
                assigneeId,
                task.getActualTime()
        );
    }

    public Task getTaskById(int taskId) {
        String sql = "SELECT * FROM task WHERE task_id = ?";
        return jdbc.queryForObject(sql, taskRowMapper, taskId);
    }

    public void updateTask(Task task) {
        String sql = "UPDATE task SET title = ?, task_description = ?, task_status = ?, estimated_time = ?, assignee_id = ?, actual_time = ? WHERE task_id = ?";
        Integer assigneeId = task.getAssignee() != null ? task.getAssignee().getUserId() : null;
        jdbc.update(sql,
                task.getTitle(),
                task.getDescription(),
                task.getStatus() != null ? task.getStatus().name() : "NoStatus",
                task.getEstimate(),
                assigneeId,
                task.getActualTime(),
                task.getTaskId()
        );
    }
}

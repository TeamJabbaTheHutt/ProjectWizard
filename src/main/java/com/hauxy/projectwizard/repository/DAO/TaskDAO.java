package com.hauxy.projectwizard.repository.DAO;

import com.hauxy.projectwizard.model.Task;
import com.hauxy.projectwizard.repository.rowMapper.TaskRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TaskDAO {

    @Autowired
    private final JdbcTemplate jdbc;
    private final TaskRowMapper taskRowMapper = new TaskRowMapper();


    public TaskDAO(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }


    public List<Task> getTasksByProjectId(int projectId) {
        String sql = "SELECT * FROM task WHERE project_id = ?";
        List<Task> tasks = jdbc.query(sql, taskRowMapper, projectId);
        return tasks;
    }

    public int insertTask(Task task, Integer parentTaskId) {
        String sql = "INSERT INTO task (title, description, project_id, parent_task_id) VALUES (?, ?, ?, ?)";
        return jdbc.update(sql, task.getTitle(), task.getDescription(), task.getProjectId(), parentTaskId);
    }

    public Task getTaskById(int taskId) {
        String sql = "SELECT * FROM task WHERE task_id = ?";
        return jdbc.queryForObject(sql, taskRowMapper, taskId);
    }





}

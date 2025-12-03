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


    public List<Task> getTasksBySubprojectId(int subprojectId) {
        String sql = "SELECT * FROM task WHERE parent_id = ?";
        List<Task> tasks = jdbc.query(sql, taskRowMapper, subprojectId);
        return tasks;
    }

    public int insertTask(Task task, Integer subprojectId) {
        String sql = "INSERT INTO task (title, description, parent_id) VALUES (?, ?, ?, ?)";
        return jdbc.update(sql, task.getTitle(), task.getDescription(), subprojectId);
    }

//    public Task getTaskById(int taskId, int subprojectId, int projectId) {
//
//        String sql = "SELECT * FROM task WHERE task_id = ?";
//        return jdbc.queryForObject(sql, taskRowMapper, taskId);
//    }





}

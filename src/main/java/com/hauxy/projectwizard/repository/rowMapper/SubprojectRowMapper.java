package com.hauxy.projectwizard.repository.rowMapper;

import com.hauxy.projectwizard.model.Subproject;
import com.hauxy.projectwizard.model.Subtask;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SubprojectRowMapper implements RowMapper<Subproject> {

    @Override
    public Subproject mapRow(ResultSet rs, int rowNum) throws SQLException {
        Subproject subproject = new Subproject();
        subproject.setSubProjectId(rs.getInt("sub_project_id"));
        subproject.setParentId(rs.getInt("parent_id"));
        subproject.setProjectId(rs.getInt("project_id"));
        subproject.setTitle(rs.getString("subproject_title"));
        subproject.setDescription(rs.getString("sub_project_description"));
        subproject.setDeadline(rs.getDate("deadline").toLocalDate());
        return subproject;
    }
}

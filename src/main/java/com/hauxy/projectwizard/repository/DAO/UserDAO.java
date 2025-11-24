package com.hauxy.projectwizard.repository.DAO;
import com.hauxy.projectwizard.model.User;
import com.hauxy.projectwizard.repository.rowMapper.UserRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class UserDAO {

    private final JdbcTemplate jdbc;
    private final UserRowMapper userRowMapper = new UserRowMapper();

    public UserDAO(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }


    public User getUserByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        try {
            return jdbc.queryForObject(sql, userRowMapper, email);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}

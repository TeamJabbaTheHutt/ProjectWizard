package com.hauxy.projectwizard.repository;

import com.hauxy.projectwizard.model.User;
import com.hauxy.projectwizard.repository.DAO.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {
    private final UserDAO userDAO;

    public UserRepository(UserDAO userDAO) {
        this.userDAO = userDAO;
    }


    public User getUserByEmail(String email) {
        return userDAO.getUserByEmail(email);
    }
}

package com.hauxy.projectwizard.service;

import com.hauxy.projectwizard.model.User;
import com.hauxy.projectwizard.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    // CREATE
    public String createNewUser(User newUser) {
        if (userRepository.createNewUser(newUser) == 1) {
            return "Success";
        } else {
            return "Failed";
        }
    }

    public boolean checkIfUserExist(String email) {
        return userRepository.getUserByEmail(email) != null;
    }

    public User getUserByEmail(String email) {
        return userRepository.getUserByEmail(email);
    }

    public User getUserById(int userId) {
        return userRepository.getUserById(userId);
    }
}

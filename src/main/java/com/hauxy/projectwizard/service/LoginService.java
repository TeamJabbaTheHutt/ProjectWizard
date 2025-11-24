package com.hauxy.projectwizard.service;

import com.hauxy.projectwizard.model.User;
import com.hauxy.projectwizard.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    private final UserRepository userRepository;

    public LoginService(UserRepository userRepository) {
        this.userRepository = userRepository;

    }

    public User checkCredentials(String email, String password) {
        User user = userRepository.getUserByEmail(email);

        if (user == null || !user.getPassword().equals(password)) {
            return null;
        }
        return user;
    }

}

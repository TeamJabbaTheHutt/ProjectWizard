package com.hauxy.projectwizard;

import com.hauxy.projectwizard.model.User;
import com.hauxy.projectwizard.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@SpringBootTest
@ActiveProfiles("test")
@Sql(scripts = "classpath:h2init.sql", executionPhase  = BEFORE_TEST_METHOD)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;


    @Test
    void createNewUser() {
        User userTest = new User("Andreas","test@mail.com","password");
        userRepository.createNewUser(userTest);
        User user = userRepository.getUserByEmail("test@mail.com");
        assertThat(user.getUsername().equals("Andreas"));
        assertThat(user.getUserId()==2);
        assertThat(user.getPassword().equals("password"));
    }

    @Test
    void fetchRightUserTest() {
        User userTest = new User("admin","adminEmail@email.com","admin");
        User user = userRepository.getUserByEmail("adminEmail@email.com");
        assertThat(user.getUsername().equals("admin"));
        assertThat(user.getPassword().equals("admin"));
        assertThat(user.getEmail().equals("adminEmail@email.com"));

    }

}

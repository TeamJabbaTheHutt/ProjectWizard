package com.hauxy.projectwizard;


import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.junit.jupiter.api.Test;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@SpringBootTest
@ActiveProfiles("test")
@Sql(scripts = "classpath:h2init.sql", executionPhase = BEFORE_TEST_METHOD)
public class TestIntegrationtestSetup {

    @Test
    void contextLoads() {
        // hvis vi når hertil, så har setup af den midlertidige h2 database i memory blevet sat op korrekt
    }
}

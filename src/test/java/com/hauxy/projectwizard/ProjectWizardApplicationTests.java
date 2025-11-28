package com.hauxy.projectwizard;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;


@SpringBootTest
@ActiveProfiles("test") // sat til test, så den ikke puller dev eller prod url da den ikke indeholder jdbc
class ProjectWizardApplicationTests {
    @Test
    void contextLoads() {}
}

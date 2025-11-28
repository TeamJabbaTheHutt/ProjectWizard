package com.hauxy.projectwizard;

import com.hauxy.projectwizard.controller.LoginController;
import com.hauxy.projectwizard.model.User;
import com.hauxy.projectwizard.service.LoginService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.verify;


@WebMvcTest(LoginController.class)
class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private LoginService loginService;

    @Test
    void wrongLoginReturnsLoginView() throws Exception {
        // Arrange
        when(loginService.checkCredentials("wrong@test.com", "bad")).thenReturn(null);

        // Act and assert
        mockMvc.perform(post("/login")
                        .param("email", "wrong@test.com")
                        .param("password", "bad"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"))
                .andExpect(model().attributeExists("message"));

        // verify
        verify(loginService).checkCredentials("wrong@test.com", "bad");
    }

    @Test
    void correctLoginRedirectsToDashboard() throws Exception {
        // Arrange
        User user = new User("test@test.com", "test", "123");
        when(loginService.checkCredentials("test@test.com", "123")).thenReturn(user);

        // Act and assert
        mockMvc.perform(post("/login")
                        .param("email", "test@test.com")
                        .param("password", "123"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/project/home"));

        // Verify
        verify(loginService).checkCredentials("test@test.com", "123");
    }

}

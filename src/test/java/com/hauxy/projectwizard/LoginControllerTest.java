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

@WebMvcTest(LoginController.class)
class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private LoginService loginService;

    @Test
    void wrongLoginReturnsLoginView() throws Exception {
        when(loginService.checkCredentials("wrong@test.com", "bad")).thenReturn(null);

        mockMvc.perform(post("/login")
                        .param("email", "wrong@test.com")
                        .param("password", "bad"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"))
                .andExpect(model().attributeExists("message"));
    }

    @Test
    void correctLoginRedirectsToDashboard() throws Exception {
        User user = new User("test@test.com", "test", "123");
        when(loginService.checkCredentials("test@test.com", "123")).thenReturn(user);

        mockMvc.perform(post("/login")
                        .param("email", "test@test.com")
                        .param("password", "123"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/dashboard"));
    }

}

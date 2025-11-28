package com.hauxy.projectwizard;

import com.hauxy.projectwizard.controller.UserController;
import com.hauxy.projectwizard.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @Test
    void testRegisterUserSuccess() throws Exception {
        Mockito.when(userService.checkIfUserExist("test@mail.com"))
                .thenReturn(false);

        Mockito.when(userService.createNewUser(any()))
                .thenReturn("Success");

        mockMvc.perform(post("/users/register")
                        .param("username", "andreas")
                        .param("email", "test@mail.com")
                        .param("password", "1234"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/project/home"));
    }

}

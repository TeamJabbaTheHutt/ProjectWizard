package com.hauxy.projectwizard;


import com.hauxy.projectwizard.controller.ProjectController;
import com.hauxy.projectwizard.model.Project;
import com.hauxy.projectwizard.model.User;
import com.hauxy.projectwizard.service.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.verify;
import java.time.LocalDate;
import java.util.List;


import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProjectController.class)
public class ProjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProjectService projectService;
    @MockitoBean
    private UserService userService;


    @Test
    void getProjectByUserIdTestListContainsProjects() throws Exception {


        User testUser = new User();
        testUser.setUserId(1);


        Project project = new Project();
        project.setProjectId(1);
        project.setTitle("projectTestTitle");
        project.setDescription("projectTestDescription");
        project.setDeadline(LocalDate.of(2025, 1, 1));

        List<Project> projects = List.of(project);
        when(projectService.getUsersProjectsByUserId(1)).thenReturn(projects);


        mockMvc.perform(get("/project/home")
                        .sessionAttr("loggedInUser", testUser))
                .andExpect(status().isOk())
                .andExpect(view().name("homepage"))
                .andExpect(model().attributeExists("UsersListOfProjects"));

        verify(projectService).getUsersProjectsByUserId(1);
    }



}

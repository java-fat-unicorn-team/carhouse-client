package com.carhouse.controller;

import com.carhouse.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class LoginControllerTest {

    @InjectMocks
    private LoginController loginController;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(loginController).build();
    }

    @Test
    void getLoginPage() throws Exception {
        mockMvc.perform(get("/loginPage"))
                .andExpect(status().isOk())
                .andExpect(view().name("loginpage"))
                .andExpect(model().attributeExists("user"));
    }

    @Test
    void submitLogin() throws Exception {
        mockMvc.perform(post("/loginPage")
                .flashAttr("user", new User()))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/homePage"));
    }
}
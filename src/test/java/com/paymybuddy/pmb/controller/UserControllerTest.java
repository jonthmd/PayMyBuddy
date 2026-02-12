package com.paymybuddy.pmb.controller;

import com.paymybuddy.pmb.repository.UserRepository;
import com.paymybuddy.pmb.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private UserRepository userRepository;

    @Test
    @WithMockUser
    void registerForm() throws Exception {

        mockMvc.perform(get("/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"))
                .andExpect(model().attributeExists("user"));
    }

    @Test
    @WithMockUser
    void register() throws Exception {

        mockMvc.perform(post("/register")
                        .param("username", "jon")
                        .param("email", "jon@test.com")
                        .param("password", "jon123")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }

    @Test
    @WithMockUser
    void registerEmailError() throws Exception {

        when(userRepository.existsByEmail("jon@test.com")).thenReturn(true);

        mockMvc.perform(post("/register")
                        .param("username", "jon")
                        .param("email", "jon@test.com")
                        .param("password", "jon123")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("register"));
    }

    @Test
    @WithMockUser
    void registerBindingResultError() throws Exception {

        mockMvc.perform(post("/register")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("register"));
    }

    @Test
    void loginForm() throws Exception {

        mockMvc.perform(get("/login"))
                .andExpect(status().isOk());
    }
}
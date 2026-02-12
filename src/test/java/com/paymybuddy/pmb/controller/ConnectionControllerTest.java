package com.paymybuddy.pmb.controller;

import com.paymybuddy.pmb.service.ConnectionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ConnectionController.class)
class ConnectionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ConnectionService connectionService;

    @Test
    @WithMockUser
    void connection() throws Exception {

        mockMvc.perform(get("/connections"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("addConnectionDTO"))
                .andExpect(view().name("connections"));
    }

    @Test
    @WithMockUser
    void addConnection() throws Exception {

        mockMvc.perform(post("/connections")
                        .param("email", "jon@test.com")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/connections"));
    }

    @Test
    @WithMockUser
    void addConnectionBindingResultError() throws Exception {

        mockMvc.perform(post("/connections")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/connections"));
    }
}
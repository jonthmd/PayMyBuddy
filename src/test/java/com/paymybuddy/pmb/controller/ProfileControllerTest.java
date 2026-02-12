package com.paymybuddy.pmb.controller;

import com.paymybuddy.pmb.dto.ProfileDTO;
import com.paymybuddy.pmb.service.ProfileService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProfileController.class)
class ProfileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProfileService profileService;

    @Test
    @WithMockUser("jon")
    void profile() throws Exception {

        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setUsername("jon");
        profileDTO.setEmail("jon@test.com");

        when(profileService.getProfile("jon")).thenReturn(profileDTO);

        mockMvc.perform(get("/profile"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("profileDTO"))
                .andExpect(view().name("profile"));
    }

    @Test
    @WithMockUser
    void updateProfile() throws Exception {

        mockMvc.perform(post("/profile")
                        .param("password", "jon123")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/profile"));
    }

    @Test
    @WithMockUser
    void updateProfileBindingResultError() throws Exception {

        mockMvc.perform(post("/profile")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/profile"));
    }
}
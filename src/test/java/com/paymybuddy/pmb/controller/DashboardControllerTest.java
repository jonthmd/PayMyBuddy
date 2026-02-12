package com.paymybuddy.pmb.controller;

import com.paymybuddy.pmb.dto.UserDTO;
import com.paymybuddy.pmb.service.TransactionService;
import com.paymybuddy.pmb.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.HashSet;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DashboardController.class)
class DashboardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private TransactionService transactionService;

    @Test
    @WithMockUser("jon")
    void dashboard() throws Exception {

        UserDTO userDTO = new UserDTO();
        when(userService.getUserByUsername("jon")).thenReturn(userDTO);

        userDTO.setConnections(new HashSet<>());
        when(transactionService.getTransactionsByUsername("jon")).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/dashboard"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attributeExists("connections"))
                .andExpect(model().attributeExists("transactions"))
                .andExpect(model().attributeExists("submitTransactionDTO"))
                .andExpect(model().attributeExists("balance"))
                .andExpect(view().name("dashboard"));
    }

    @Test
    @WithMockUser("jon")
    void submitTransaction() throws Exception {

        UserDTO userDTO = new UserDTO();
        when(userService.getUserByUsername("jon")).thenReturn(userDTO);

        mockMvc.perform(post("/transaction")
                        .param("amount", "100")
                        .param("receiver", "noj")
                        .param("description", "TEST")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/dashboard"));
    }

    @Test
    @WithMockUser("jon")
    void submitTransactionBindingResultError() throws Exception {

        UserDTO userDTO = new UserDTO();
        when(userService.getUserByUsername("jon")).thenReturn(userDTO);

        mockMvc.perform(post("/transaction")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/dashboard"));
    }

    @Test
    @WithMockUser
    void depositBalance() throws Exception {

        mockMvc.perform(post("/balance")
                        .param("amount", "100")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/dashboard"));
    }

    @Test
    @WithMockUser
    void depositBalanceBindingResultError() throws Exception {

        mockMvc.perform(post("/balance")
                        .param("amount", "0")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/dashboard"));
    }
}
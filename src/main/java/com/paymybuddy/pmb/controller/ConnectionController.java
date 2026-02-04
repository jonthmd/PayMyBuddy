package com.paymybuddy.pmb.controller;

import com.paymybuddy.pmb.dto.*;
import com.paymybuddy.pmb.service.ConnectionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Slf4j
@Controller
public class ConnectionController {

    private final ConnectionService connectionService;

    public ConnectionController(ConnectionService connectionService) {
        this.connectionService = connectionService;
    }

    @GetMapping("/connections")
    public String connection(Model model) {
        model.addAttribute("addConnectionDTO", new AddConnectionDTO());
        return "connections";
    }

    @PostMapping("/connections")
    public String addConnection(@ModelAttribute AddConnectionDTO addConnectionDTO, Principal principal){

        connectionService.createConnection(principal.getName(), addConnectionDTO.getEmail());

        return "redirect:/connections";

    }

}

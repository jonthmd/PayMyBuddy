package com.paymybuddy.pmb.controller;

import com.paymybuddy.pmb.dto.*;
import com.paymybuddy.pmb.exceptions.ContactAlreadyAddedException;
import com.paymybuddy.pmb.exceptions.ContactNotFoundException;
import com.paymybuddy.pmb.exceptions.ImpossibleConnectionException;
import com.paymybuddy.pmb.service.ConnectionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

        log.info("Adding connections");

        return "connections";
    }

    @PostMapping("/connections")
    public String addConnection(@ModelAttribute AddConnectionDTO addConnectionDTO, Principal principal, RedirectAttributes redirectAttributes) {

        try {
            connectionService.createConnection(principal.getName(), addConnectionDTO.getEmail());
            redirectAttributes.addFlashAttribute("message", "Contact ajouté !");

        } catch (ContactAlreadyAddedException | ImpossibleConnectionException | ContactNotFoundException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/connections";
    }

}

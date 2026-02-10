package com.paymybuddy.pmb.controller;

import com.paymybuddy.pmb.dto.*;
import com.paymybuddy.pmb.exceptions.ContactAlreadyAddedException;
import com.paymybuddy.pmb.exceptions.ContactNotFoundException;
import com.paymybuddy.pmb.exceptions.ImpossibleConnectionException;
import com.paymybuddy.pmb.service.ConnectionService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

/**
 * Controller related to adding contact.
 */
@Slf4j
@Controller
public class ConnectionController {

    private final ConnectionService connectionService;

    public ConnectionController(ConnectionService connectionService) {
        this.connectionService = connectionService;
    }

    /**
     * Displays the connection page.
     *
     * @param model Model used to transmit data.
     * @return The connections view.
     */
    @GetMapping("/connections")
    public String connection(Model model) {

        model.addAttribute("addConnectionDTO", new AddConnectionDTO());

        log.info("Adding connections");

        return "connections";
    }

    /**
     * Validates the connection adding form.
     *
     * @param addConnectionDTO   DTO used to represent contact data.
     * @param bindingResult      The data validation, displays error message if any.
     * @param principal          The current logged user.
     * @param redirectAttributes Attributes used to display message after redirect.
     * @return A redirect to the connections page.
     */
    @PostMapping("/connections")
    public String addConnection(@Valid @ModelAttribute AddConnectionDTO addConnectionDTO, BindingResult bindingResult, Principal principal, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("error", bindingResult.getAllErrors().get(0).getDefaultMessage());

            return "redirect:/connections";
        }

        try {
            connectionService.createConnection(principal.getName(), addConnectionDTO.getEmail());
            redirectAttributes.addFlashAttribute("message", "Contact ajouté !");
        } catch (ContactAlreadyAddedException | ImpossibleConnectionException | ContactNotFoundException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/connections";
    }

}

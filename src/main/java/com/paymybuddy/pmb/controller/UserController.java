package com.paymybuddy.pmb.controller;

import com.paymybuddy.pmb.dto.RegisterDTO;
import com.paymybuddy.pmb.repository.UserRepository;
import com.paymybuddy.pmb.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    /**
     * Displays the register page.
     *
     * @param model Model used to transmit data.
     * @return The register view.
     */
    @GetMapping("/register")
    public String registerForm(Model model) {

        model.addAttribute("user", new RegisterDTO());

        log.info("Registering form");

        return "register";
    }

    /**
     * Validates the registration.
     *
     * @param registerDTO   DTO used to represent a user data for registration.
     * @param bindingResult The data validation, displays error message if any.
     * @param model         Model used to transmit data.
     * @return A redirect to the register view.
     */
    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("user") RegisterDTO registerDTO, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("error", bindingResult.getAllErrors().get(0).getDefaultMessage());

            return "register";
        }

        if (userRepository.existsByEmail(registerDTO.getEmail())) {
            model.addAttribute("error", "Email déjà utilisé.");

            return "register";
        }

        userService.createUser(registerDTO);
        log.info("Registered user: {}", registerDTO);

        return "redirect:/login";
    }

    /**
     * Displays the login page.
     *
     * @return The login view.
     */
    @GetMapping("/login")
    public String loginForm() {

        log.info("Login form");

        return "login";
    }

}

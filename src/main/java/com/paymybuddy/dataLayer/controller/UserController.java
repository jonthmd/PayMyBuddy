package com.paymybuddy.dataLayer.controller;

import com.paymybuddy.dataLayer.configuration.CustomUserDetailsService;
import com.paymybuddy.dataLayer.dto.RegisterDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
public class UserController {

    private final CustomUserDetailsService customUserDetailsService;

    public UserController(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("user", new RegisterDTO());
        log.info("Registering form");
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("user") RegisterDTO registerDTO, Model model) {
        customUserDetailsService.createUser(registerDTO);
        log.info("Registered user: {}", registerDTO);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginForm() {
        log.info("Login form");
        return "login";
    }

}

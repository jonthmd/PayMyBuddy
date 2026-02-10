package com.paymybuddy.pmb.controller;

import com.paymybuddy.pmb.dto.RegisterDTO;
import com.paymybuddy.pmb.repository.UserRepository;
import com.paymybuddy.pmb.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    @GetMapping("/register")
    public String registerForm(Model model) {

        model.addAttribute("user", new RegisterDTO());

        log.info("Registering form");

        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("user") RegisterDTO registerDTO, Model model) {

        if (userRepository.existsByEmail(registerDTO.getEmail())) {
            model.addAttribute("error", "Email déjà utilisé.");

            return "register";
        }

        userService.createUser(registerDTO);
        log.info("Registered user: {}", registerDTO);

        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginForm() {

        log.info("Login form");

        return "login";
    }

}

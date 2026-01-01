package com.paymybuddy.dataLayer.controller;

import com.paymybuddy.dataLayer.dto.UserDTO;
import com.paymybuddy.dataLayer.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
//@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user/add")
    public UserDTO addUser(UserDTO userDTO) {
        return userService.createUser(userDTO);
    }

    @PutMapping
    public UserDTO findUserByEmail(String email) {
        return userService.getUserByEmail(email);
    }

    @PutMapping("/user/{id}")
    public UserDTO findUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @DeleteMapping
    public void deleteUserById(Long id) {
        userService.deleteUser(id);
    }
}

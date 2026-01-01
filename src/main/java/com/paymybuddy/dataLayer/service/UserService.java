package com.paymybuddy.dataLayer.service;

import com.paymybuddy.dataLayer.dto.UserDTO;

import java.math.BigDecimal;

/**
 * Service interface managing operations related to user.
 */
public interface UserService {

    UserDTO createUser(UserDTO userDTO);
    UserDTO getUserByEmail(String email);
    UserDTO getUserById(Long id);
    void deleteUser(Long id);
    UserDTO balanceUpdate(UserDTO userDTO, BigDecimal balance);

}

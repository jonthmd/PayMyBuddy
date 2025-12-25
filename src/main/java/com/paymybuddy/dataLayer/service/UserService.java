package com.paymybuddy.dataLayer.service;

import com.paymybuddy.dataLayer.dto.UserDTO;

import java.math.BigDecimal;
import java.util.List;

/**
 * Service interface managing operations related to user.
 */
public interface UserService {

    UserDTO createUser(UserDTO userDTO);
    List<UserDTO> getAllUsers();
    UserDTO getUserByEmail(String email);
    UserDTO getUserById(Long id);
    void deleteUser(Long id);
    UserDTO walletUpdate(UserDTO userDTO, BigDecimal wallet);

}

package com.paymybuddy.pmb.service;

import com.paymybuddy.pmb.dto.RegisterDTO;
import com.paymybuddy.pmb.dto.UserDTO;

/**
 * Service interface managing operations related to user.
 */
public interface UserService {

    void createUser(RegisterDTO registerDTO);
    UserDTO getUserByUsername(String username);

}

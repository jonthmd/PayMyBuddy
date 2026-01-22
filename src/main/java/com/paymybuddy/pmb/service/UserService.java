package com.paymybuddy.pmb.service;

import com.paymybuddy.pmb.dto.RegisterDTO;
import com.paymybuddy.pmb.dto.UserDTO;

public interface UserService {

    void createUser(RegisterDTO registerDTO);
    UserDTO getUserByUsername(String username);

}

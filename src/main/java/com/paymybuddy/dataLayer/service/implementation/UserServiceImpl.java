package com.paymybuddy.dataLayer.service.implementation;

import com.paymybuddy.dataLayer.dto.UserDTO;
import com.paymybuddy.dataLayer.mapper.UserMapper;
import com.paymybuddy.dataLayer.model.User;
import com.paymybuddy.dataLayer.repository.UserRepository;
import com.paymybuddy.dataLayer.service.UserService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Implementations of the user service interface.
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }


    /**
     * @param userDTO Mapped object containing the created user details.
     * @return The created user.
     */
    @Override
    public UserDTO createUser(UserDTO userDTO) {
        User user = userMapper.userDTOToUser(userDTO);
        userRepository.save(user);
        return userMapper.userToUserDTO(user);
    }

    /**
     * @param email Email of the user.
     * @return The user searched by the specified email.
     */
    @Override
    public UserDTO getUserByEmail(String email) {
        return userRepository.findAll()
                .stream()
                .filter(user -> user.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .map(userMapper::userToUserDTO)
                .orElse(null);
    }

    /**
     * @param id Unique identifier of the user.
     * @return The user searched by the specific id.
     */
    @Override
    public UserDTO getUserById(Long id) {
        return userMapper.userToUserDTO(userRepository.findById(id).orElse(null));
    }

    /**
     * @param id Unique identifier of the user.
     */
    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    /**
     * @param userDTO Mapped object containing the created user details.
     * @param balance Balance of the wallet user.
     * @return The user with the updated balance.
     */
    @Override
    public UserDTO balanceUpdate(UserDTO userDTO, BigDecimal balance) {
        return null;
    }
}

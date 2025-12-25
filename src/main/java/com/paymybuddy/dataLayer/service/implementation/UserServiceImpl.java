package com.paymybuddy.dataLayer.service.implementation;

import com.paymybuddy.dataLayer.dto.UserDTO;
import com.paymybuddy.dataLayer.mapper.UserMapper;
import com.paymybuddy.dataLayer.model.User;
import com.paymybuddy.dataLayer.repository.UserRepository;
import com.paymybuddy.dataLayer.service.UserService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

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
     * @return UserDTO, the created user.
     */
    @Override
    public UserDTO createUser(UserDTO userDTO) {
        User user = userMapper.userDTOToUser(userDTO);
        userRepository.save(user);
        return userMapper.userToUserDTO(user);
    }

    /**
     * @return All users on the database.
     */
    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::userToUserDTO)
                .toList();
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
     * @return  The user searched by the specific id.
     */
    @Override
    public UserDTO getUserById(Long id) {
        return userMapper.userToUserDTO(userRepository.findById(id).orElse(null));
    }

    /**
     * @param id
     */
    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    /**
     * @param userDTO
     * @param wallet
     * @return
     */
    @Override
    public UserDTO walletUpdate(UserDTO userDTO, BigDecimal wallet) {
        return null;
    }
}

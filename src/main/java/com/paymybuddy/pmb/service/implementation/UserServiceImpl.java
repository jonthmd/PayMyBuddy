package com.paymybuddy.pmb.service.implementation;

import com.paymybuddy.pmb.dto.ConnectionDTO;
import com.paymybuddy.pmb.dto.RegisterDTO;
import com.paymybuddy.pmb.dto.UserDTO;
import com.paymybuddy.pmb.mapper.UserMapper;
import com.paymybuddy.pmb.model.User;
import com.paymybuddy.pmb.repository.UserRepository;
import com.paymybuddy.pmb.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *Implementations of the user service interface.
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


    /**
     * @param registerDTO Mapped object containing data to register.
     */
    @Override
    public void createUser(RegisterDTO registerDTO) {

            String password = bCryptPasswordEncoder.encode(registerDTO.getPassword());

            User user = new User();
            user.setUsername(registerDTO.getUsername());
            user.setEmail(registerDTO.getEmail());
            user.setPassword(password);
            user.setBalance(BigDecimal.ZERO);

            userRepository.save(user);
    }

    /**
     * @param username Unique username of the user.
     * @return Mapped object containing data of the user.
     */
    @Override
    public UserDTO getUserByUsername(String username) {

        User user = userRepository.findByUsername(username);
        UserDTO userDTO = userMapper.userToUserDTO(user);

        Set<ConnectionDTO> connections = user.getConnections().stream()
                .map(connection -> {
                    User friend = connection.getFriend();
                    return new ConnectionDTO(friend.getId(), friend.getUsername(), friend.getBalance());
                }).collect(Collectors.toSet());

        userDTO.setConnections(connections);

        return userDTO;
    }
}


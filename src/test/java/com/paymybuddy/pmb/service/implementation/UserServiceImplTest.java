package com.paymybuddy.pmb.service.implementation;

import com.paymybuddy.pmb.dto.RegisterDTO;
import com.paymybuddy.pmb.dto.UserDTO;
import com.paymybuddy.pmb.mapper.UserMapper;
import com.paymybuddy.pmb.model.Connection;
import com.paymybuddy.pmb.model.User;
import com.paymybuddy.pmb.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @InjectMocks
    private UserServiceImpl classUnderTest;

    @Test
    void createUser() {

        //GIVEN
        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setUsername("jon");
        registerDTO.setPassword("jon123");
        registerDTO.setEmail("jon@test.com");

        when(bCryptPasswordEncoder.encode("jon123")).thenReturn(registerDTO.getPassword());

        //WHEN
        classUnderTest.createUser(registerDTO);

        //THEN
        verify(bCryptPasswordEncoder).encode("jon123");
        assertThat(registerDTO.getUsername()).isEqualTo("jon");
        assertThat(registerDTO.getEmail()).isEqualTo("jon@test.com");
        assertThat(registerDTO.getPassword()).isEqualTo(bCryptPasswordEncoder.encode("jon123"));
    }

    @Test
    void getUserByUsername() {

        //GIVEN
        User user = new User();
        user.setUsername("jon");

        User user2 = new User();
        user2.setUsername("noj");

        UserDTO userDTO = new UserDTO();

        Connection connection = new Connection();
        connection.setFriend(user2);
        Set<Connection> connections = Set.of(connection);

        user.setConnections(connections);

        when(userRepository.findByUsername("jon")).thenReturn(user);
        when(userMapper.userToUserDTO(user)).thenReturn(userDTO);

        //WHEN
        UserDTO result = classUnderTest.getUserByUsername("jon");

        //THEN
        verify(userRepository).findByUsername("jon");
        assertThat(result).isEqualTo(userDTO);
    }
}
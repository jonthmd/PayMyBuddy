package com.paymybuddy.pmb.service.implementation;

import com.paymybuddy.pmb.exceptions.ContactAlreadyAddedException;
import com.paymybuddy.pmb.exceptions.ContactNotFoundException;
import com.paymybuddy.pmb.exceptions.ImpossibleConnectionException;
import com.paymybuddy.pmb.model.User;
import com.paymybuddy.pmb.repository.ConnectionRepository;
import com.paymybuddy.pmb.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ConnectionServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ConnectionRepository connectionRepository;

    @InjectMocks
    private ConnectionServiceImpl classUnderTest;

    @Test
    void createConnection() {

        //GIVEN
        User user = new User();
        user.setUsername("jon");
        user.setEmail("jon@test.com");

        User user2 = new User();
        user2.setUsername("noj");
        user2.setEmail("noj@test.com");

        when(userRepository.findByUsername("jon")).thenReturn(user);
        when(userRepository.findByEmail("noj@test.com")).thenReturn(user2);

        //WHEN
        classUnderTest.createConnection("jon", "noj@test.com");

        //THEN
        verify(userRepository).findByUsername("jon");
        verify(userRepository).findByEmail("noj@test.com");
    }

    @Test
    void createConnectionContactNotFound() {

        //GIVEN
        User user = new User();
        user.setUsername("jon");
        user.setEmail("jon@test.com");

        when(userRepository.findByUsername("jon")).thenReturn(user);
        when(userRepository.findByEmail("noj@test.com")).thenReturn(null);

        //WHEN+THEN
        assertThrows(ContactNotFoundException.class, () -> classUnderTest.createConnection("jon", "noj@test.com"));
    }

    @Test
    void createConnectionContactAlreadyExists() {

        //GIVEN
        User user = new User();
        user.setUsername("jon");
        user.setEmail("jon@test.com");

        User user2 = new User();
        user2.setUsername("noj");
        user2.setEmail("noj@test.com");

        when(connectionRepository.existsByUserAndFriend(user, user2)).thenReturn(true);
        when(userRepository.findByUsername("jon")).thenReturn(user);
        when(userRepository.findByEmail("noj@test.com")).thenReturn(user2);

        //WHEN+THEN
        assertThrows(ContactAlreadyAddedException.class, () -> classUnderTest.createConnection("jon", "noj@test.com"));
    }

    @Test
    void createConnectionImpossibleConnection() {

        //GIVEN
        User user = new User();
        user.setUsername("jon");
        user.setEmail("jon@test.com");

        User user2 = new User();
        user2.setUsername("jon");
        user2.setEmail("jon@test.com");

        when(userRepository.findByUsername("jon")).thenReturn(user);
        when(userRepository.findByEmail("jon@test.com")).thenReturn(user2);

        //WHEN+THEN
        assertThrows(ImpossibleConnectionException.class, () -> classUnderTest.createConnection("jon", "jon@test.com"));
    }
}
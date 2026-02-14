package com.paymybuddy.pmb.service.implementation;

import com.paymybuddy.pmb.dto.ProfileDTO;
import com.paymybuddy.pmb.exceptions.InvalidPasswordException;
import com.paymybuddy.pmb.model.User;
import com.paymybuddy.pmb.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProfileServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @InjectMocks
    private ProfileServiceImpl classUnderTest;

    @Test
    void getProfile() {

        //GIVEN
        User user = new User();
        user.setUsername("jon");
        user.setEmail("jon@test.com");

        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setUsername(user.getUsername());

        when(userRepository.findByUsername("jon")).thenReturn(user);

        //WHEN
        ProfileDTO result = classUnderTest.getProfile(user.getUsername());

        //THEN
        verify(userRepository).findByUsername("jon");
        assertEquals(profileDTO.getUsername(), result.getUsername());
    }

    @Test
    void updatePassword() {

        //GIVEN
        User user = new User();
        user.setUsername("jon");
        user.setPassword("jon123");

        when(userRepository.findByUsername("jon")).thenReturn(user);
        when(bCryptPasswordEncoder.encode("jon123")).thenReturn(user.getPassword());

        //WHEN
        classUnderTest.updatePassword(user.getUsername(), bCryptPasswordEncoder.encode(user.getPassword()));

        //THEN
        verify(userRepository).findByUsername("jon");
        assertThat(user.getUsername()).isEqualTo("jon");
        assertThat(user.getPassword()).isEqualTo(bCryptPasswordEncoder.encode("jon123"));
    }

    @Test
    void updatePasswordInvalidPassword() {

        //GIVEN
        User user = new User();
        user.setUsername("jon");

        //WHEN+THEN
        assertThrows(InvalidPasswordException.class, () -> classUnderTest.updatePassword(user.getUsername(), user.getPassword()));
    }
}
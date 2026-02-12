package com.paymybuddy.pmb.repository;

import com.paymybuddy.pmb.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void findByUsername() {

       User user = new User();
       user.setUsername("jon");
       user.setEmail("jon@test.com");
       user.setPassword("jon123");
       userRepository.save(user);

       User result = userRepository.findByUsername(user.getUsername());

       assertEquals(user.getUsername(), result.getUsername());
    }

    @Test
    void findByEmail() {

        User user = new User();
        user.setUsername("jon");
        user.setEmail("jon@test.com");
        user.setPassword("jon123");
        userRepository.save(user);

        User result = userRepository.findByEmail(user.getEmail());

        assertEquals(user.getEmail(), result.getEmail());
    }

    @Test
    void existsByEmail() {

        User user = new User();
        user.setUsername("jon");
        user.setEmail("jon@test.com");
        user.setPassword("jon123");
        userRepository.save(user);

        boolean result = userRepository.existsByEmail(user.getEmail());

        assertTrue(result);
    }
}
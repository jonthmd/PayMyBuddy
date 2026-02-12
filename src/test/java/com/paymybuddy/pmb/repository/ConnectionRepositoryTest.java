package com.paymybuddy.pmb.repository;

import com.paymybuddy.pmb.model.Connection;
import com.paymybuddy.pmb.model.ConnectionID;
import com.paymybuddy.pmb.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ConnectionRepositoryTest {

    @Autowired
    private ConnectionRepository connectionRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void existsByUserAndFriend() {

        User user = new User();
        user.setUsername("jon");
        userRepository.save(user);

        User user2 = new User();
        user2.setUsername("noj");
        userRepository.save(user2);

        ConnectionID connectionID = new ConnectionID(user.getId(), user2.getId());

        Connection connection = new Connection();
        connection.setId(connectionID);
        connection.setUser(user);
        connection.setFriend(user2);
        connectionRepository.save(connection);

        boolean result = connectionRepository.existsByUserAndFriend(user, user2);

        assertTrue(result);
    }
}
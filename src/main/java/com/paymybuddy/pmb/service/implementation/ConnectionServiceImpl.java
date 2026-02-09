package com.paymybuddy.pmb.service.implementation;

import com.paymybuddy.pmb.exceptions.ContactAlreadyAddedException;
import com.paymybuddy.pmb.exceptions.ContactNotFoundException;
import com.paymybuddy.pmb.exceptions.ImpossibleConnectionException;
import com.paymybuddy.pmb.model.Connection;
import com.paymybuddy.pmb.model.User;
import com.paymybuddy.pmb.repository.ConnectionRepository;
import com.paymybuddy.pmb.repository.UserRepository;
import com.paymybuddy.pmb.service.ConnectionService;
import org.springframework.stereotype.Service;

@Service
public class ConnectionServiceImpl implements ConnectionService {

    private final ConnectionRepository connectionRepository;
    private final UserRepository userRepository;

    public ConnectionServiceImpl(ConnectionRepository connectionRepository, UserRepository userRepository) {
        this.connectionRepository = connectionRepository;
        this.userRepository = userRepository;
    }


    /**
     * @param username The username of the current user.
     * @param email    The email of the contact user.
     */
    @Override
    public void createConnection(String username, String email) {

        User user = userRepository.findByUsername(username);
        User friend = userRepository.findByEmail(email);

        if (friend == null) {
            throw new ContactNotFoundException("Contact inexistant.");
        }

        if (connectionRepository.existsByUserAndFriend(user, friend)) {
            throw new ContactAlreadyAddedException("Contact déjà ajouté.");
        }

        if (user.getEmail().equals(friend.getEmail())) {
            throw new ImpossibleConnectionException("Ajout non autorisé.");
        }

        Connection connection = new Connection(user, friend);
        connectionRepository.save(connection);

    }
}

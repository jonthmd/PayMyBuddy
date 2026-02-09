package com.paymybuddy.pmb.service.implementation;

import com.paymybuddy.pmb.exceptions.InvalidPasswordException;
import com.paymybuddy.pmb.model.User;
import com.paymybuddy.pmb.repository.UserRepository;
import com.paymybuddy.pmb.service.ProfileService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ProfileServiceImpl implements ProfileService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public ProfileServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


    /**
     * @param username The username of the current user.
     * @param password The user password to update.
     */
    @Override
    public void updatePassword(String username, String password) {

        if (password==null || password.isEmpty()) {
            throw new InvalidPasswordException("Mot de passe invalide.");
        }

        User user = userRepository.findByUsername(username);
        user.setPassword(bCryptPasswordEncoder.encode(password));
        userRepository.save(user);

    }
}

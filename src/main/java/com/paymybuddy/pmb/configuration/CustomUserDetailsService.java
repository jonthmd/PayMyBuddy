package com.paymybuddy.pmb.configuration;

import com.paymybuddy.pmb.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Spring Security configuration used to load user during authentication.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Loads user by the username.
     *
     * @param username The user username.
     * @return A user with authorities.
     * @throws UsernameNotFoundException If the user is not found.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        com.paymybuddy.pmb.model.User user = userRepository.findByEmail(username);

        if (user == null) {
            throw new UsernameNotFoundException("L'email "  + username + " est inexistant.");
        }

        return new User(user.getUsername(), user.getPassword(), getGrantedAuthorities());
    }

    /**
     * Returns authorities of the user.
     *
     * @return A list of authorities.
     */
    private List<GrantedAuthority> getGrantedAuthorities() {

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        return authorities;
    }
}

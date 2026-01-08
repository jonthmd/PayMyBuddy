package com.paymybuddy.dataLayer.configuration;

import com.paymybuddy.dataLayer.dto.UserDTO;
import com.paymybuddy.dataLayer.mapper.UserMapper;
import com.paymybuddy.dataLayer.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public CustomUserDetailsService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.paymybuddy.dataLayer.model.User user = userRepository.findByUsername(username);
        return new User(user.getUsername(), user.getPassword(), getGrantedAuthorities());
    }

    private List<GrantedAuthority> getGrantedAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        return authorities;
    }

    public UserDTO createUser(UserDTO userDTO) {
        com.paymybuddy.dataLayer.model.User user = userMapper.userDTOToUser(userDTO);
        com.paymybuddy.dataLayer.model.User savedUser = userRepository.save(user);
        return userMapper.userToUserDTO(savedUser);
    }

    public UserDTO getByUsername(String username) {
        com.paymybuddy.dataLayer.model.User user = userRepository.findByUsername(username);
        return userMapper.userToUserDTO(user);
    }

}

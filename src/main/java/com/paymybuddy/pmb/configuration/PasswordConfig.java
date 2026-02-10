package com.paymybuddy.pmb.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Configuration for password encoding.
 */
@Configuration
public class PasswordConfig {

    /**
     * Bean of the password encoder.
     *
     * @return A encode.
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }
}

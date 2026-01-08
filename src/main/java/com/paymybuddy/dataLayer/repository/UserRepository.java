package com.paymybuddy.dataLayer.repository;

import com.paymybuddy.dataLayer.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for managing {@link User}.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    public User findByUsername(String username);
}

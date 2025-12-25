package com.paymybuddy.dataLayer.repository;

import com.paymybuddy.dataLayer.model.Connection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for managing {@link Connection}.
 */
@Repository
public interface ConnectionRepository extends JpaRepository<Connection, Long> {
}

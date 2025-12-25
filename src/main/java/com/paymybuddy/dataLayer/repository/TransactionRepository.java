package com.paymybuddy.dataLayer.repository;

import com.paymybuddy.dataLayer.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for managing {@link Transaction}.
 */
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}

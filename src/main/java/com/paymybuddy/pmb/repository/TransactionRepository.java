package com.paymybuddy.pmb.repository;

import com.paymybuddy.pmb.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for managing {@link Transaction}.
 */
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findBySenderUsernameOrReceiverUsername(String senderUsername, String receiverUsername);

}

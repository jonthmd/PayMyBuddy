package com.paymybuddy.pmb.service;

import com.paymybuddy.pmb.dto.TransactionDTO;

import java.math.BigDecimal;
import java.util.List;

/**
 * Service interface managing operations related to transaction.
 */
public interface TransactionService {

    void depositBalance(String username, BigDecimal amount);
    void createTransaction(BigDecimal amount, String senderUsername, String receiverUsername, String description);
    List<TransactionDTO> getTransactionsByUsername(String username);

}

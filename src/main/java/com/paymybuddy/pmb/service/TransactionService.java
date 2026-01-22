package com.paymybuddy.pmb.service;

import com.paymybuddy.pmb.dto.TransactionDTO;

import java.util.List;

/**
 * Service interface managing operations related to transaction.
 */
public interface TransactionService {

    TransactionDTO createTransaction(TransactionDTO transactionDTO);
    List<TransactionDTO> getTransactionsByUsername(String username);

}

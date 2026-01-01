package com.paymybuddy.dataLayer.service;

import com.paymybuddy.dataLayer.dto.TransactionDTO;

/**
 * Service interface managing operations related to transaction.
 */
public interface TransactionService {

    TransactionDTO createTransaction(TransactionDTO transactionDTO);

}

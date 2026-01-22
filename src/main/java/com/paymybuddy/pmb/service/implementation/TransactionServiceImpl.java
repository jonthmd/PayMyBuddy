package com.paymybuddy.pmb.service.implementation;

import com.paymybuddy.pmb.dto.TransactionDTO;
import com.paymybuddy.pmb.mapper.TransactionMapper;
import com.paymybuddy.pmb.model.Transaction;
import com.paymybuddy.pmb.repository.TransactionRepository;
import com.paymybuddy.pmb.service.TransactionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;


    public TransactionServiceImpl(TransactionRepository transactionRepository, TransactionMapper transactionMapper) {
        this.transactionRepository = transactionRepository;
        this.transactionMapper = transactionMapper;
    }


    /**
     * @param transactionDTO
     * @return
     */
    @Override
    public TransactionDTO createTransaction(TransactionDTO transactionDTO) {
        return null;
    }

    /**
     * @param username The username of the specified user.
     * @return A list of transactions of the specified user.
     */
    @Override
    public List<TransactionDTO> getTransactionsByUsername(String username) {
        List<Transaction> transactions =
                transactionRepository
                        .findBySenderUsernameOrReceiverUsername(username, username);

        return transactions.stream()
                .map(transaction -> {
                    TransactionDTO transactionDTO = transactionMapper.transactionToTransactionDTO(transaction);

                    if (transaction.getSender().getUsername().equals(username)) {
                        transactionDTO.setSignedAmount("-" + transaction.getAmount());
                        transactionDTO.setRelation(transaction.getReceiver().getUsername());
                    } else {
                        transactionDTO.setSignedAmount("+" + transaction.getAmount());
                        transactionDTO.setRelation(transaction.getSender().getUsername());
                    }

                    return transactionDTO;
                })
                .toList();

    }

}

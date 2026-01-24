package com.paymybuddy.pmb.service.implementation;

import com.paymybuddy.pmb.dto.TransactionDTO;
import com.paymybuddy.pmb.mapper.TransactionMapper;
import com.paymybuddy.pmb.model.Transaction;
import com.paymybuddy.pmb.model.User;
import com.paymybuddy.pmb.repository.TransactionRepository;
import com.paymybuddy.pmb.repository.UserRepository;
import com.paymybuddy.pmb.service.TransactionService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;
    private final UserRepository userRepository;


    public TransactionServiceImpl(TransactionRepository transactionRepository, TransactionMapper transactionMapper, UserRepository userRepository) {
        this.transactionRepository = transactionRepository;
        this.transactionMapper = transactionMapper;
        this.userRepository = userRepository;
    }

    private static final BigDecimal COMMISSION = new BigDecimal("0.005");

    /**
     *
     */
    @Override
    public void createTransaction(BigDecimal amount, String senderUsername, String receiverUsername) {

        BigDecimal commission = amount.multiply(COMMISSION);
        BigDecimal total = amount.add(commission);

        User sender = userRepository.findByUsername(senderUsername);
        User receiver = userRepository.findByUsername(receiverUsername);
        if (sender.getBalance().compareTo(total) >= 0) {
            throw new IllegalArgumentException("Solde insuffisant.");
        }

        sender.setBalance(sender.getBalance().subtract(total));
        receiver.setBalance(receiver.getBalance().add(amount));

        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setSender(sender);
        transaction.setReceiver(receiver);
        transaction.setTransactionDate(LocalDateTime.now());

        transactionRepository.save(transaction);
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
                        transactionDTO.setSignedAmount("- " + transaction.getAmount() + "€");
                        transactionDTO.setRelation(transaction.getReceiver().getUsername());
                    } else {
                        transactionDTO.setSignedAmount("+ " + transaction.getAmount() + "€");
                        transactionDTO.setRelation(transaction.getSender().getUsername());
                    }

                    return transactionDTO;
                })
                .toList();

    }

}

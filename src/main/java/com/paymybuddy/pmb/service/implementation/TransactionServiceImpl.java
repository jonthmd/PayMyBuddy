package com.paymybuddy.pmb.service.implementation;

import com.paymybuddy.pmb.dto.TransactionDTO;
import com.paymybuddy.pmb.exceptions.InsufficientBalanceException;
import com.paymybuddy.pmb.exceptions.InvalidAmountException;
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
     * @param username The username of the current user.
     * @param amount   The amount to added to the balance.
     */
    @Override
    public void depositBalance(String username, BigDecimal amount) {

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidAmountException("Montant invalide.");
        }

        User user = userRepository.findByUsername(username);

        BigDecimal commission = amount.multiply(COMMISSION); //amount is null
        BigDecimal total = amount.subtract(commission);

        user.setBalance(user.getBalance().add(total));

        Transaction transaction = new Transaction();
        transaction.setSender(user);
        transaction.setReceiver(user);
        transaction.setAmount(amount);
        transaction.setDescription("Recharge du compte de +" + amount + " €");
        transaction.setTransactionDate(LocalDateTime.now());

        transactionRepository.save(transaction);
        userRepository.save(user);
    }

    /**
     * @param amount           The amount of the transaction to be transferred.
     * @param senderUsername   The username of the sender user.
     * @param receiverUsername The username of the receiver user.
     * @param description      The description of the transaction.
     */
    @Override
    public void createTransaction(BigDecimal amount, String senderUsername, String receiverUsername, String description) {

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidAmountException("Montant invalide.");
        }

        BigDecimal commission = amount.multiply(COMMISSION);
        BigDecimal total = amount.add(commission);

        User sender = userRepository.findByUsername(senderUsername);
        User receiver = userRepository.findByUsername(receiverUsername);

        if (sender.getBalance().compareTo(total) <= 0) {
            throw new InsufficientBalanceException("Solde insuffisant.");
        }

        sender.setBalance(sender.getBalance().subtract(total));
        receiver.setBalance(receiver.getBalance().add(amount));

        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setSender(sender);
        transaction.setReceiver(receiver);
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setDescription("Paiement à " + receiver.getUsername() + " : " + description);

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

                    if (transaction.getSender().getUsername().equals(username) && transaction.getReceiver().getUsername().equals(username)) {
                        transactionDTO.setSignedAmount("+ " + transaction.getAmount() + "€");
                        transactionDTO.setRelation("-");
                    } else if (transaction.getReceiver().getUsername().equals(username)) {
                        transactionDTO.setSignedAmount("+ " + transaction.getAmount() + "€");
                        transactionDTO.setRelation(transaction.getSender().getUsername());
                    } else if (transaction.getSender().getUsername().equals(username)) {
                        transactionDTO.setSignedAmount("- " + transaction.getAmount() + "€");
                        transactionDTO.setRelation(transaction.getReceiver().getUsername());
                    }

                    return transactionDTO;
                })
                .toList();

    }

}

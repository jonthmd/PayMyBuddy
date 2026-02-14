package com.paymybuddy.pmb.repository;

import com.paymybuddy.pmb.model.Transaction;
import com.paymybuddy.pmb.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class TransactionRepositoryTest {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void findBySenderUsernameOrReceiverUsername() {

        //GIVEN
        User sender = new User();
        sender.setUsername("noj");
        userRepository.save(sender);

        User receiver = new User();
        receiver.setUsername("jon");
        userRepository.save(receiver);

        Transaction transaction = new Transaction();
        transaction.setSender(sender);
        transaction.setReceiver(receiver);
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setAmount(BigDecimal.ONE);
        transaction.setDescription("test");
        transactionRepository.save(transaction);

        //WHEN
        List<Transaction> result = transactionRepository.findBySenderUsernameOrReceiverUsername("noj", "jon");

        //THEN
        assertEquals(1, result.size());
        assertEquals("test", result.getFirst().getDescription());
    }
}
package com.paymybuddy.pmb.service.implementation;

import com.paymybuddy.pmb.dto.TransactionDTO;
import com.paymybuddy.pmb.exceptions.InsufficientBalanceException;
import com.paymybuddy.pmb.exceptions.InvalidAmountException;
import com.paymybuddy.pmb.mapper.TransactionMapper;
import com.paymybuddy.pmb.model.Transaction;
import com.paymybuddy.pmb.model.User;
import com.paymybuddy.pmb.repository.TransactionRepository;
import com.paymybuddy.pmb.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private TransactionMapper transactionMapper;

    @InjectMocks
    private TransactionServiceImpl classUnderTest;

    private static final BigDecimal COMMISSION = new BigDecimal("0.005");

    @Test
    void depositBalance() {

        //GIVEN
        BigDecimal balance = BigDecimal.valueOf(100);
        BigDecimal amount = BigDecimal.TEN;

        BigDecimal commission = amount.multiply(COMMISSION);
        BigDecimal total = amount.subtract(commission);

        User user = new User();
        user.setUsername("jon");
        user.setBalance(balance);

        Transaction transaction = new Transaction();
        transaction.setSender(user);
        transaction.setReceiver(user);
        transaction.setAmount(amount);
        transaction.setTransactionDate(LocalDateTime.now());

        when(userRepository.findByUsername("jon")).thenReturn(user);

        //WHEN
        classUnderTest.depositBalance(user.getUsername(), amount);

        //THEN
        verify(userRepository).findByUsername("jon");
        assertThat(user.getBalance()).isEqualTo(balance.add(total));
    }

    @Test
    void depositBalanceInvalidAmount() {

        //GIVEN
        BigDecimal balance = BigDecimal.valueOf(100);
        BigDecimal amount = BigDecimal.ZERO;

        User user = new User();
        user.setUsername("jon");
        user.setBalance(balance);

        Transaction transaction = new Transaction();
        transaction.setSender(user);
        transaction.setReceiver(user);
        transaction.setAmount(amount);
        transaction.setTransactionDate(LocalDateTime.now());

        //WHEN+THEN
        assertThrows(InvalidAmountException.class, () -> classUnderTest.depositBalance(user.getUsername(), amount));
    }

    @Test
    void createTransaction() {

        //GIVEN
        BigDecimal balance = BigDecimal.valueOf(100);
        BigDecimal amount = BigDecimal.TEN;

        BigDecimal commission = amount.multiply(COMMISSION);
        BigDecimal total = amount.add(commission);

        BigDecimal balance2 = BigDecimal.valueOf(100);

        User user = new User();
        user.setUsername("jon");
        user.setBalance(balance);

        User user2 = new User();
        user2.setUsername("noj");
        user2.setBalance(balance2);

        Transaction transaction = new Transaction();
        transaction.setSender(user);
        transaction.setReceiver(user2);
        transaction.setAmount(amount);
        transaction.setDescription("TEST");
        transaction.setTransactionDate(LocalDateTime.now());

        when(userRepository.findByUsername("jon")).thenReturn(user);
        when(userRepository.findByUsername("noj")).thenReturn(user2);

        //WHEN
        classUnderTest.createTransaction(amount, user.getUsername(), user2.getUsername(), transaction.getDescription());

        //THEN
        verify(userRepository).findByUsername("jon");
        verify(userRepository).findByUsername("noj");
        assertThat(user.getBalance()).isEqualTo(balance.subtract(total));
        assertThat(user2.getBalance()).isEqualTo(balance2.add(amount));
    }

    @Test
    void createTransactionInvalidAmount() {

        //GIVEN
        BigDecimal balance = BigDecimal.valueOf(100);
        BigDecimal amount = BigDecimal.ZERO;

        BigDecimal balance2 = BigDecimal.valueOf(100);

        User user = new User();
        user.setUsername("jon");
        user.setBalance(balance);

        User user2 = new User();
        user2.setUsername("noj");
        user2.setBalance(balance2);

        Transaction transaction = new Transaction();
        transaction.setSender(user);
        transaction.setReceiver(user2);
        transaction.setAmount(amount);
        transaction.setDescription("TEST");
        transaction.setTransactionDate(LocalDateTime.now());

        //WHEN+THEN
        assertThrows(InvalidAmountException.class, () -> classUnderTest.createTransaction(amount, user.getUsername(), user2.getUsername(), transaction.getDescription()));
    }

    @Test
    void createTransactionInsufficientBalance() {

        //GIVEN
        BigDecimal balance = BigDecimal.valueOf(0);
        BigDecimal amount = BigDecimal.TEN;

        BigDecimal balance2 = BigDecimal.valueOf(100);

        User user = new User();
        user.setUsername("jon");
        user.setBalance(balance);

        User user2 = new User();
        user2.setUsername("noj");
        user2.setBalance(balance2);

        Transaction transaction = new Transaction();
        transaction.setSender(user);
        transaction.setReceiver(user2);
        transaction.setAmount(amount);
        transaction.setDescription("TEST");
        transaction.setTransactionDate(LocalDateTime.now());

        when(userRepository.findByUsername("jon")).thenReturn(user);
        when(userRepository.findByUsername("noj")).thenReturn(user2);

        //WHEN+THEN
        assertThrows(InsufficientBalanceException.class, () -> classUnderTest.createTransaction(amount, user.getUsername(), user2.getUsername(), transaction.getDescription()));
    }

    @Test
    void getTransactionsByUsernameSenderReceiver() {

        //GIVEN
        User user = new User();
        user.setUsername("jon");

        Transaction transaction = new Transaction();
        transaction.setSender(user);
        transaction.setReceiver(user);

        List<Transaction> transactions = List.of(transaction);
        TransactionDTO transactionDTO = new TransactionDTO();


        when(transactionRepository.findBySenderUsernameOrReceiverUsername(user.getUsername(), user.getUsername())).thenReturn(transactions);
        when(transactionMapper.transactionToTransactionDTO(transaction)).thenReturn(transactionDTO);

        //WHEN
        List<TransactionDTO> result = classUnderTest.getTransactionsByUsername(user.getUsername());

        //THEN
        verify(transactionRepository).findBySenderUsernameOrReceiverUsername(user.getUsername(), user.getUsername());
        verify(transactionMapper).transactionToTransactionDTO(transaction);
        assertThat(result).hasSize(1);
    }

    @Test
    void getTransactionsByUsernameReceiver() {

        //GIVEN
        User user = new User();
        user.setUsername("jon");

        User user2 = new User();
        user2.setUsername("noj");

        Transaction transaction = new Transaction();
        transaction.setSender(user2);
        transaction.setReceiver(user);

        List<Transaction> transactions = List.of(transaction);
        TransactionDTO transactionDTO = new TransactionDTO();


        when(transactionRepository.findBySenderUsernameOrReceiverUsername(user.getUsername(), user.getUsername())).thenReturn(transactions);
        when(transactionMapper.transactionToTransactionDTO(transaction)).thenReturn(transactionDTO);

        //WHEN
        List<TransactionDTO> result = classUnderTest.getTransactionsByUsername(user.getUsername());

        //THEN
        verify(transactionRepository).findBySenderUsernameOrReceiverUsername(user.getUsername(), user.getUsername());
        verify(transactionMapper).transactionToTransactionDTO(transaction);
        assertThat(result).hasSize(1);
    }

    @Test
    void getTransactionsByUsernameSender() {

        //GIVEN
        User user = new User();
        user.setUsername("jon");

        User user2 = new User();
        user2.setUsername("noj");

        Transaction transaction = new Transaction();
        transaction.setSender(user);
        transaction.setReceiver(user2);

        List<Transaction> transactions = List.of(transaction);
        TransactionDTO transactionDTO = new TransactionDTO();


        when(transactionRepository.findBySenderUsernameOrReceiverUsername(user.getUsername(), user.getUsername())).thenReturn(transactions);
        when(transactionMapper.transactionToTransactionDTO(transaction)).thenReturn(transactionDTO);

        //WHEN
        List<TransactionDTO> result = classUnderTest.getTransactionsByUsername(user.getUsername());

        //THEN
        verify(transactionRepository).findBySenderUsernameOrReceiverUsername(user.getUsername(), user.getUsername());
        verify(transactionMapper).transactionToTransactionDTO(transaction);
        assertThat(result).hasSize(1);
    }
}
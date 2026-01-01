package com.paymybuddy.dataLayer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO used to represent a transaction.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDTO {

    private Long id;
    private String description;
    private BigDecimal amount;
    private LocalDateTime transactionDate;
    private UserDTO sender;
    private UserDTO receiver;

}

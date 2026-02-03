package com.paymybuddy.pmb.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubmitTransactionDTO {

    private String receiver;
    private String description;
    private BigDecimal amount;

}

package com.paymybuddy.pmb.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * DTO used to represent a deposit amount.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WalletDTO {

    @NotNull(message = "Le montant est obligatoire.")
    @DecimalMin(value = "0.01", message = "Le montant doit être supérieur à 0.")
    private BigDecimal amount;

}

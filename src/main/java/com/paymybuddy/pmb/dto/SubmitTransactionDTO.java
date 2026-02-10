package com.paymybuddy.pmb.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * DTO used to represent a transaction with a user.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubmitTransactionDTO {

    @NotBlank(message = "Le contact est obligatoire.")
    private String receiver;

    @Size(max = 35, message = "La description ne doit pas dépasser 35 caractères")
    private String description;

    @NotNull(message = "Le montant est obligatoire.")
    @DecimalMin(value = "0.01", message = "Le montant doit être supérieur à 0.")
    private BigDecimal amount;

}

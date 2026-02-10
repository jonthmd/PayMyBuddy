package com.paymybuddy.pmb.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO used to represent contact data.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddConnectionDTO {

    @NotBlank(message = "L’email est obligatoire.")
    @Email(message = "Email invalide.")
    private String email;

}

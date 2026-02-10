package com.paymybuddy.pmb.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO used to represent a user profile.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileDTO {

    private String username;
    private String email;

    @NotBlank(message = "Le mot de passe est obligatoire.")
    @Size(min = 4, message = "Le mot de passe doit contenir au moins 4 caractères.")
    private String password;

}

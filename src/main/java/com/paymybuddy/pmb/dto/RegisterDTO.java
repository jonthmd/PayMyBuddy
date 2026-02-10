package com.paymybuddy.pmb.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO used to represent a user data for registration.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDTO {

    @NotBlank(message = "Le nom d'utilisateur obligatoire.")
    @Size(min = 3, max = 20, message = "Le nom d'utilisateur doit contenir entre 3 et 20 caractères.")
    private String username;

    @NotBlank(message = "L’email est obligatoire.")
    @Email(message = "Email invalide.")
    private String email;

    @NotBlank(message = "Le mot de passe est obligatoire.")
    @Size(min = 4, message = "Le mot de passe doit contenir au moins 4 caractères.")
    private String password;

}

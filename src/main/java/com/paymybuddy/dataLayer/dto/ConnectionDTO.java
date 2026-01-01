package com.paymybuddy.dataLayer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO used to represent a connection between users.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConnectionDTO {

    private UserDTO user;
    private UserDTO friend;

}

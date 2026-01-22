package com.paymybuddy.pmb.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * DTO used to represent a connection between users.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConnectionDTO {

    private Long friendId;
    private String friendUsername;
    private BigDecimal friendBalance;

}

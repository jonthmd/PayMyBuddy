package com.paymybuddy.dataLayer.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Set;

/**
 * Model representing a user in the database.
 */
@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private Long id;

    private String username;

    private String email;

    private String password;

    private BigDecimal balance;

    @OneToMany(mappedBy = "user")
    private Set<Connection> connections;

}

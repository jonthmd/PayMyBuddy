package com.paymybuddy.dataLayer.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Model representing a connection between two users in the database.
 */
@Getter
@Setter
@Entity
@Table(name = "connections")
public class Connection {

    @EmbeddedId
    private ConnectionID id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @MapsId("friendId")
    @JoinColumn(name = "friend_id")
    private User friend;

}

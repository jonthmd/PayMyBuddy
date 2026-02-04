package com.paymybuddy.pmb.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Model representing a connection between two users in the database.
 */
@Getter
@Setter
@NoArgsConstructor
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

    public Connection(User user, User friend) {
        this.user = user;
        this.friend = friend;
        this.id = new ConnectionID(user.getId(), friend.getId());
    }

}

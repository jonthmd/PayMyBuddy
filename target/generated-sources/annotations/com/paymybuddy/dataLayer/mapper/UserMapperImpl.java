package com.paymybuddy.dataLayer.mapper;

import com.paymybuddy.dataLayer.dto.ConnectionDTO;
import com.paymybuddy.dataLayer.dto.UserDTO;
import com.paymybuddy.dataLayer.model.Connection;
import com.paymybuddy.dataLayer.model.User;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-31T21:09:01+0100",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.9 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDTO userToUserDTO(User user) {
        if ( user == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( user.getId() );
        userDTO.setUsername( user.getUsername() );
        userDTO.setEmail( user.getEmail() );
        userDTO.setBalance( user.getBalance() );
        userDTO.setConnections( connectionSetToConnectionDTOSet( user.getConnections() ) );

        return userDTO;
    }

    @Override
    public User userDTOToUser(UserDTO userDTO) {
        if ( userDTO == null ) {
            return null;
        }

        User user = new User();

        user.setId( userDTO.getId() );
        user.setUsername( userDTO.getUsername() );
        user.setEmail( userDTO.getEmail() );
        user.setBalance( userDTO.getBalance() );
        user.setConnections( connectionDTOSetToConnectionSet( userDTO.getConnections() ) );

        return user;
    }

    protected ConnectionDTO connectionToConnectionDTO(Connection connection) {
        if ( connection == null ) {
            return null;
        }

        ConnectionDTO connectionDTO = new ConnectionDTO();

        connectionDTO.setUser( userToUserDTO( connection.getUser() ) );
        connectionDTO.setFriend( userToUserDTO( connection.getFriend() ) );

        return connectionDTO;
    }

    protected Set<ConnectionDTO> connectionSetToConnectionDTOSet(Set<Connection> set) {
        if ( set == null ) {
            return null;
        }

        Set<ConnectionDTO> set1 = new LinkedHashSet<ConnectionDTO>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( Connection connection : set ) {
            set1.add( connectionToConnectionDTO( connection ) );
        }

        return set1;
    }

    protected Connection connectionDTOToConnection(ConnectionDTO connectionDTO) {
        if ( connectionDTO == null ) {
            return null;
        }

        Connection connection = new Connection();

        connection.setUser( userDTOToUser( connectionDTO.getUser() ) );
        connection.setFriend( userDTOToUser( connectionDTO.getFriend() ) );

        return connection;
    }

    protected Set<Connection> connectionDTOSetToConnectionSet(Set<ConnectionDTO> set) {
        if ( set == null ) {
            return null;
        }

        Set<Connection> set1 = new LinkedHashSet<Connection>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( ConnectionDTO connectionDTO : set ) {
            set1.add( connectionDTOToConnection( connectionDTO ) );
        }

        return set1;
    }
}

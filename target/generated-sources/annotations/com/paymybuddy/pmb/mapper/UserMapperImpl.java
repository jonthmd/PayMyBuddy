package com.paymybuddy.pmb.mapper;

import com.paymybuddy.pmb.dto.ConnectionDTO;
import com.paymybuddy.pmb.dto.UserDTO;
import com.paymybuddy.pmb.model.Connection;
import com.paymybuddy.pmb.model.User;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-02-12T13:58:00+0100",
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

        return connectionDTO;
    }

    protected Set<ConnectionDTO> connectionSetToConnectionDTOSet(Set<Connection> set) {
        if ( set == null ) {
            return null;
        }

        Set<ConnectionDTO> set1 = LinkedHashSet.newLinkedHashSet( set.size() );
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

        return connection;
    }

    protected Set<Connection> connectionDTOSetToConnectionSet(Set<ConnectionDTO> set) {
        if ( set == null ) {
            return null;
        }

        Set<Connection> set1 = LinkedHashSet.newLinkedHashSet( set.size() );
        for ( ConnectionDTO connectionDTO : set ) {
            set1.add( connectionDTOToConnection( connectionDTO ) );
        }

        return set1;
    }
}

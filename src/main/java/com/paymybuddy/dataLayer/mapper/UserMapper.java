package com.paymybuddy.dataLayer.mapper;

import com.paymybuddy.dataLayer.dto.UserDTO;
import com.paymybuddy.dataLayer.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO userToUserDTO(User user);
    User userDTOToUser(UserDTO userDTO);

}

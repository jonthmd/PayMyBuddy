package com.paymybuddy.pmb.mapper;

import com.paymybuddy.pmb.dto.UserDTO;
import com.paymybuddy.pmb.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO userToUserDTO(User user);
    User userDTOToUser(UserDTO userDTO);

}

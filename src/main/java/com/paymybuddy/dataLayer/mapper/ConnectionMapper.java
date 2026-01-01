package com.paymybuddy.dataLayer.mapper;

import com.paymybuddy.dataLayer.dto.ConnectionDTO;
import com.paymybuddy.dataLayer.model.Connection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ConnectionMapper {

    ConnectionDTO connectionToConnectionDTO(Connection connection);
    Connection connectionDTOToConnection(ConnectionDTO connectionDTO);

}

package com.paymybuddy.dataLayer.service;

import com.paymybuddy.dataLayer.dto.ConnectionDTO;

/**
 * Service interface managing operations related to connection.
 */
public interface ConnectionService {

    ConnectionDTO createConnection(ConnectionDTO connectionDTO);
    ConnectionDTO deleteConnection(ConnectionDTO connectionDTO);

}

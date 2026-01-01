package com.paymybuddy.dataLayer.mapper;

import com.paymybuddy.dataLayer.dto.TransactionDTO;
import com.paymybuddy.dataLayer.model.Transaction;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    TransactionDTO transactionToTransactionDTO(Transaction transaction);
    Transaction transactionDTOToTransaction(TransactionDTO transactionDTO);

}

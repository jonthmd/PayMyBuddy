package com.paymybuddy.pmb.mapper;

import com.paymybuddy.pmb.dto.TransactionDTO;
import com.paymybuddy.pmb.model.Transaction;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    TransactionDTO transactionToTransactionDTO(Transaction transaction);
    Transaction transactionDTOToTransaction(TransactionDTO transactionDTO);

}

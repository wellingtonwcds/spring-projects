package com.construo.ch.fruitsvegetables.mapper;

import com.construo.ch.fruitsvegetables.domain.Transaction;
import com.construo.ch.fruitsvegetables.dto.TransactionDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring", uses = TransactionDetailMapper.class)
public interface TransactionMapper {

    @Mappings({
            @Mapping(target = "id", source = "dto.id"),
            @Mapping(target = "customer.id", source = "dto.customerId"),
            @Mapping(target = "transactionDate", source = "dto.transactionDate"),
            @Mapping(target = "transactionTime", source = "dto.transactionTime"),
            @Mapping(target = "totalAmount", source = "dto.totalAmount"),
            @Mapping(target = "transactionDetails", source = "dto.transactionsDetails"),
    })
    Transaction toEntity(TransactionDTO dto);

    @Mappings({
            @Mapping(target = "id", source = "e.id"),
            @Mapping(target = "customerId", source = "e.customer.id"),
            @Mapping(target = "transactionDate", source = "e.transactionDate"),
            @Mapping(target = "transactionTime", source = "e.transactionTime"),
            @Mapping(target = "totalAmount", source = "e.totalAmount"),
            @Mapping(target = "transactionsDetails", source = "e.transactionDetails"),
    })
    TransactionDTO toDTO(Transaction e);

    @Mappings({
            @Mapping(target = "id", source = "e.id"),
            @Mapping(target = "customerId", source = "e.customer.id"),
            @Mapping(target = "transactionDate", source = "e.transactionDate"),
            @Mapping(target = "transactionTime", source = "e.transactionTime"),
            @Mapping(target = "totalAmount", source = "e.totalAmount"),
            @Mapping(target = "transactionsDetails", source = "e.transactionDetails"),
    })
    List<TransactionDTO> toListDTO(List<Transaction> entityList);
}

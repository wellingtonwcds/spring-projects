package com.construo.ch.fruitsvegetables.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
public class TransactionDTO {

    private Long id;

    @NotNull(message = "customerId is mandatory")
    private Long customerId;

    @NotNull(message = "transactionDate is mandatory")
    private LocalDate transactionDate;

    @NotNull(message = "transactionTime is mandatory")
    private LocalTime transactionTime;

    private Long totalAmount;

    @NotNull(message = "transactionsDetails is mandatory")
    private List<TransactionDetailDTO> transactionsDetails;
}

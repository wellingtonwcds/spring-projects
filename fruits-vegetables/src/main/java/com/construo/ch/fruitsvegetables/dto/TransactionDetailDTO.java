package com.construo.ch.fruitsvegetables.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class TransactionDetailDTO {

    private Long id;

    @NotNull(message = "productId is mandatory")
    private Long productId;

    @Min(value = 1, message = "quantity must be greater than 0")
    @NotNull(message = "quantity is mandatory")
    private Long quantity;

    private BigDecimal subTotal;
}

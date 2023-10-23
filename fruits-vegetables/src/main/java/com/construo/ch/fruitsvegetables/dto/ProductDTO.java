package com.construo.ch.fruitsvegetables.dto;

import com.construo.ch.fruitsvegetables.util.CategoryEnum;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class ProductDTO {

    private Long id;

    @NotEmpty(message = "name is mandatory")
    private String name;

    @NotNull(message = "category is mandatory")
    private CategoryEnum category;

    @Min(value = 0, message = "price must be greater than or equal to 0")
    @NotNull(message = "price is mandatory")
    private BigDecimal price;

    @Min(value = 1, message = "idMerchant must be greater than 0")
    @NotNull(message = "idMerchant is mandatory")
    private Long idMerchant;
}

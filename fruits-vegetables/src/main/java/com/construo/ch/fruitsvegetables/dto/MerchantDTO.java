package com.construo.ch.fruitsvegetables.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class MerchantDTO {

    private Long id;

    @NotEmpty(message = "name is mandatory")
    private String name;

    @NotEmpty(message = "contactInfo is mandatory")
    private String contactInfo;
}

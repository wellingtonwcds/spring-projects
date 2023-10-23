package com.construo.ch.fruitsvegetables.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class CustomerDTO {

    private long id;

    @NotEmpty(message = "firstName is mandatory")
    private String firstName;

    @NotEmpty(message = "lastName is mandatory")
    private String lastName;

    @NotEmpty(message = "contactInfo is mandatory")
    private String contactInfo;
}

package com.construo.ch.fruitsvegetables.mapper;

import com.construo.ch.fruitsvegetables.domain.Customer;
import com.construo.ch.fruitsvegetables.dto.CustomerDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    @Mappings({
            @Mapping(target = "id", source = "dto.id"),
            @Mapping(target = "firstName", source = "dto.firstName"),
            @Mapping(target = "lastName", source = "dto.lastName"),
            @Mapping(target = "contactInfo", source = "dto.contactInfo"),
    })
    Customer toEntity(CustomerDTO dto);


    @Mappings({
            @Mapping(target = "id", source = "e.id"),
            @Mapping(target = "firstName", source = "e.firstName"),
            @Mapping(target = "lastName", source = "e.lastName"),
            @Mapping(target = "contactInfo", source = "e.contactInfo"),
    })
    CustomerDTO toDTO(Customer e);


    @Mappings({
            @Mapping(target = "id", source = "dto.id"),
            @Mapping(target = "firstName", source = "dto.firstName"),
            @Mapping(target = "lastName", source = "dto.lastName"),
            @Mapping(target = "contactInfo", source = "dto.contactInfo")
    })
    List<CustomerDTO> toListDTO(List<Customer> entityList);
}

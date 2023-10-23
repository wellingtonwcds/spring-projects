package com.construo.ch.fruitsvegetables.mapper;

import com.construo.ch.fruitsvegetables.domain.Merchant;
import com.construo.ch.fruitsvegetables.dto.MerchantDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MerchantMapper {

    @Mappings({
            @Mapping(target = "id", source = "dto.id"),
            @Mapping(target = "name", source = "dto.name"),
            @Mapping(target = "contactInfo", source = "dto.contactInfo")
    })
    Merchant toEntity(MerchantDTO dto);


    @Mappings({
            @Mapping(target = "id", source = "e.id"),
            @Mapping(target = "name", source = "e.name"),
            @Mapping(target = "contactInfo", source = "e.contactInfo")
    })
    MerchantDTO toDTO(Merchant e);


    @Mappings({
            @Mapping(target = "id", source = "dto.id"),
            @Mapping(target = "name", source = "dto.name"),
            @Mapping(target = "contactInfo", source = "dto.contactInfo")
    })
    List<MerchantDTO> toListDTO(List<Merchant> entityList);
}

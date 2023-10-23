package com.construo.ch.fruitsvegetables.mapper;

import com.construo.ch.fruitsvegetables.domain.TransactionDetail;
import com.construo.ch.fruitsvegetables.dto.TransactionDetailDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TransactionDetailMapper {

    @Mappings({
            @Mapping(target = "id", source = "dto.id"),
            @Mapping(target = "product.id", source = "dto.productId"),
            @Mapping(target = "quantity", source = "dto.quantity"),
            @Mapping(target = "subTotal", source = "dto.quantity"),
    })
    TransactionDetail toEntity(TransactionDetailDTO dto);


    @Mappings({
            @Mapping(target = "id", source = "e.id"),
            @Mapping(target = "productId", source = "e.product.id"),
            @Mapping(target = "quantity", source = "e.quantity"),
            @Mapping(target = "subTotal", source = "e.subTotal"),
    })
    TransactionDetailDTO toDTO(TransactionDetail e);


    @Mappings({
            @Mapping(target = "id", source = "dto.id"),
            @Mapping(target = "product.id", source = "dto.productId"),
            @Mapping(target = "quantity", source = "dto.quantity"),
            @Mapping(target = "subTotal", source = "dto.quantity"),
    })
    List<TransactionDetailDTO> toListDTO(List<TransactionDetail> entityList);
}

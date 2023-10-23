package com.construo.ch.fruitsvegetables.mapper;

import com.construo.ch.fruitsvegetables.domain.Product;
import com.construo.ch.fruitsvegetables.dto.ProductDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mappings({
            @Mapping(target = "id", source = "dto.id"),
            @Mapping(target = "name", source = "dto.name"),
            @Mapping(target = "category", source = "dto.category"),
            @Mapping(target = "price", source = "dto.price"),
            @Mapping(target = "merchant.id", source = "dto.idMerchant"),
    })
    Product toEntity(ProductDTO dto);

    @Mappings({
            @Mapping(target = "id", source = "e.id"),
            @Mapping(target = "name", source = "e.name"),
            @Mapping(target = "category", source = "e.category"),
            @Mapping(target = "price", source = "e.price"),
            @Mapping(target = "idMerchant", source = "e.merchant.id"),
    })
    ProductDTO toDTO(Product e);

    @Mappings({
            @Mapping(target = "id", source = "dto.id"),
            @Mapping(target = "name", source = "dto.name"),
            @Mapping(target = "category", source = "dto.category"),
            @Mapping(target = "price", source = "dto.price"),
            @Mapping(target = "merchant.id", source = "dto.idMerchant"),
    })
    List<ProductDTO> toListDTO(List<Product> entityList);
}

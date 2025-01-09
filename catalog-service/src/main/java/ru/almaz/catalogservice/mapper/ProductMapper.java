package ru.almaz.catalogservice.mapper;

import ru.almaz.catalogservice.dto.ProductDto;
import ru.almaz.catalogservice.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductMapper {
    @Mapping(target = "shopId", source = "shop.id")
    ProductDto productEntityToProductDto(ProductEntity productEntity);
}


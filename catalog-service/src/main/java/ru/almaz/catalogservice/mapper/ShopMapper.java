package ru.almaz.catalogservice.mapper;

import ru.almaz.catalogservice.dto.ShopDto;
import ru.almaz.catalogservice.entity.ShopEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ShopMapper {

    ShopDto shopEntityToShopDto(ShopEntity shopEntity);
    ShopEntity shopDtoToShopEntity(ShopDto shopDto);
}

package ru.almaz.catalogservice.service;

import ru.almaz.catalogservice.exception.BadRequestException;
import ru.almaz.catalogservice.exception.NotFoundException;
import ru.almaz.catalogservice.dto.ShopDto;
import ru.almaz.catalogservice.entity.ShopEntity;
import ru.almaz.catalogservice.mapper.ShopMapper;
import ru.almaz.catalogservice.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShopService {
    private final ShopRepository shopRepository;

    private final ShopMapper shopMapper;

    public ShopDto getShopByIdOrThrow(Long id) {
        ShopEntity shopEntity = shopRepository.findById(id).orElseThrow(
                () -> new NotFoundException(String.format("Shop with id %s not found", id))
        );
        return shopMapper.shopEntityToShopDto(shopEntity);
    }

    public ShopDto addShop(String name, Optional<String> description) {
        if(name.trim().isEmpty())
            throw new BadRequestException("Shop name cannot be empty");
        shopRepository.findByName(name)
                .ifPresent(shop -> {
                    throw new BadRequestException(String.format("Shop %s  already exists", name));
                });

        ShopEntity shopEntity =  ShopEntity.builder().build();
        shopEntity.setName(name);
        description.ifPresent(shopEntity::setDescription);
        shopRepository.save(shopEntity);
        return shopMapper.shopEntityToShopDto(shopEntity);
    }

    public List<ShopDto> getAllShops() {
        return shopRepository
                .findAll()
                .stream()
                .map(shopMapper::shopEntityToShopDto)
                .collect(Collectors.toList());
    }

    public ShopDto updateShop(Long id, Optional<String> shopName,Optional<String> shopDescription ) {
        shopName = shopName.filter(name -> !name.trim().isEmpty());
        shopDescription = shopDescription.filter(description -> !description.trim().isEmpty());
        if(shopName.isEmpty() && shopDescription.isEmpty()) {
            throw new BadRequestException("Name and description cannot be empty");
        }
        ShopEntity shopEntity = shopMapper.shopDtoToShopEntity(getShopByIdOrThrow(id));
            shopName.ifPresent(name->{
                shopRepository.findByName(name)
                        .filter(anotherShop -> !Objects.equals(anotherShop.getId(), shopEntity.getId()))
                        .ifPresent(shop -> {
                            throw new BadRequestException(String.format("Shop %s  already exists", shop.getName()));
                        });
                shopEntity.setName(name);
            });
        shopDescription.ifPresent(shopEntity::setDescription);
        shopRepository.saveAndFlush(shopEntity);
        return shopMapper.shopEntityToShopDto(shopEntity);
    }

    public Boolean deleteShop(Long id) {
        getShopByIdOrThrow(id);
        shopRepository.delete(shopMapper.shopDtoToShopEntity(getShopByIdOrThrow(id)));
        return true;
    }




}

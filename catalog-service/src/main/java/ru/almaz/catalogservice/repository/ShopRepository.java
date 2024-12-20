package ru.almaz.catalogservice.repository;

import ru.almaz.catalogservice.entity.ShopEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface ShopRepository extends JpaRepository<ShopEntity,Long> {
    Optional<ShopEntity> findByName(String name);
}

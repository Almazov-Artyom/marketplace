package ru.almaz.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.almaz.userservice.entity.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
}

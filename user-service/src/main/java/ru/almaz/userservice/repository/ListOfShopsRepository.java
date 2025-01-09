package ru.almaz.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.almaz.userservice.entity.ListOfShops;

@Repository
public interface ListOfShopsRepository extends JpaRepository<ListOfShops, Long> {
}

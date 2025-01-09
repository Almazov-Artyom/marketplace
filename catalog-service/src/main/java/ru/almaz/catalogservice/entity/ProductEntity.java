package ru.almaz.catalogservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"name","shop_id"}),
        name = "products")
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private Double price;

    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "shop_id")
    private ShopEntity shop;

    @Builder.Default
    private Instant addedAt = Instant.now();

    @Builder.Default
    private Instant updatedAt = Instant.now();
}

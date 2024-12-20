package ru.almaz.catalogservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "shops")
public class ShopEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    private String description;

    @Builder.Default
    private Instant createdAt = Instant.now();

    @Builder.Default
    @OneToMany
    @JoinColumn(name = "shop_id")
    private List<ProductEntity> products = new ArrayList<>();
}

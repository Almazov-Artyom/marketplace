package ru.almaz.userservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDto {
    @NonNull
    private Long id;

    private String name;

    private String description;

    private Double price;

    private Integer quantity;

    private Long shopId;

    @JsonProperty("added_at")
    private Instant addedAt;

    @JsonProperty("updated_at")
    private Instant updatedAt;

}

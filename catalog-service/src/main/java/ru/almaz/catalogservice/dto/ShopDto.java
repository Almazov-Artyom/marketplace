package ru.almaz.catalogservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShopDto {
    @NonNull
    private Long id;

    @NonNull
    private String name;

    private String description;

    @NonNull
    @JsonProperty("created_at")
    private Instant createdAt;

    private List<ProductDto> products;

}

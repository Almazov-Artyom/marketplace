package ru.almaz.userservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.almaz.userservice.dto.ProductDto;

@FeignClient(name = "catalog-service")
public interface CatalogServiceClient {
    @GetMapping("/api/product/{product_id}/exist")
    ResponseEntity<Boolean> exist(@PathVariable("product_id") Long productId);

    @PostMapping("/api/product/{product_id}/{quantity}")
    ProductDto changeQuantity(@PathVariable("product_id") Long productId, @PathVariable("quantity") Integer quantity);


}

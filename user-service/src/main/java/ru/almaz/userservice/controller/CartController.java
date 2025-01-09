package ru.almaz.userservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.almaz.userservice.dto.ProductDto;
import ru.almaz.userservice.service.CartService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user/cart")
public class CartController {
    private final CartService cartService;

    @PostMapping("/{product_id}/{quantity}")
    public ResponseEntity<ProductDto> addProductToCart(@PathVariable(name = "product_id") Long productId,
                                                       @PathVariable (name = "quantity") Integer quantity){
        return ResponseEntity.ok(cartService.addProductToCart(productId, quantity));
    }

}

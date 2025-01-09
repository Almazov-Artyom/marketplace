package ru.almaz.catalogservice.сontroller;


import org.springframework.http.ResponseEntity;
import ru.almaz.catalogservice.dto.ProductDto;
import ru.almaz.catalogservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ProductController {

    final ProductService productService;

    @PostMapping("/shops/{shop_id}/product")
    public ProductDto addProduct(@PathVariable(name = "shop_id") Long id, @RequestBody ProductDto productDto){
        return productService.addProductAtShop(id,productDto);
    }

    @GetMapping("/shops/{shop_id}/products")
    public List<ProductDto> getAllProducts(@PathVariable(name = "shop_id") Long id){
        return productService.getAllProductsFromShopById(id);
    }

    @DeleteMapping("/product/{product_id}")
    public Boolean deleteProduct(@PathVariable(name = "product_id") Long id){
        return productService.deleteProductById(id);
    }

    @PatchMapping("/product/{product_id}")
    public ProductDto updateProduct(@PathVariable(name = "product_id") Long id, @RequestBody ProductDto productDto){
        return productService.updateProductById(id,productDto);
    }

    @GetMapping("/product/{product_id}/exist")
    public ResponseEntity<Boolean> existProduct(@PathVariable(name = "product_id") Long id){
        return ResponseEntity.ok(productService.existById(id));
    }

    @PostMapping("/product/{product_id}/{quantity}")
    public ResponseEntity<ProductDto> changeQuantity(@PathVariable(name = "product_id") Long id,
            @PathVariable(name = "quantity") Integer quantity){
        return ResponseEntity.ok(productService.changeQuantity(id, quantity));
    }







}

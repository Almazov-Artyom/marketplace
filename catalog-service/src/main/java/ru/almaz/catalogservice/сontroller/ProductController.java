package ru.almaz.catalogservice.сontroller;


import ru.almaz.catalogservice.dto.ProductDto;
import ru.almaz.catalogservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductController {

    final ProductService productService;

    @PostMapping("api/shops/{shop_id}/product")
    public ProductDto addProduct(@PathVariable(name = "shop_id") Long id, @RequestBody ProductDto productDto){
        return productService.addProductAtShop(id,productDto);
    }

    @GetMapping("api/shops/{shop_id}/products")
    public List<ProductDto> getAllProducts(@PathVariable(name = "shop_id") Long id){
        return productService.getAllProductsFromShopById(id);
    }

    @DeleteMapping("api/product/{product_id}")
    public Boolean deleteProduct(@PathVariable(name = "product_id") Long id){
        return productService.deleteProductById(id);
    }

    @PatchMapping("api/product/{product_id}")
    public ProductDto updateProduct(@PathVariable(name = "product_id") Long id, @RequestBody ProductDto productDto){
        return productService.updateProductById(id,productDto);
    }


}

package ru.almaz.catalogservice.service;

import jakarta.transaction.Transactional;
import ru.almaz.catalogservice.exception.BadRequestException;
import ru.almaz.catalogservice.dto.ProductDto;
import ru.almaz.catalogservice.entity.ProductEntity;
import ru.almaz.catalogservice.entity.ShopEntity;
import ru.almaz.catalogservice.mapper.ProductMapper;
import ru.almaz.catalogservice.mapper.ShopMapper;
import ru.almaz.catalogservice.repository.ProductRepository;
import ru.almaz.catalogservice.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    private final ShopRepository shopRepository;

    private final ProductMapper productMapper;

    private final ShopMapper shopMapper;

    private final ShopService shopService;

    @Transactional
    public ProductDto getProductByIdOrThrow(Long id) {
        ProductEntity productEntity = productRepository.findById(id)
                .orElseThrow(()->new BadRequestException
                        (String.format("Product with id %s not found", id)));
        return productMapper.productEntityToProductDto(productEntity);
    }

    @Transactional
    public ProductDto addProductAtShop(Long id, ProductDto productDto) {

       ShopEntity shopEntity = shopMapper.shopDtoToShopEntity(shopService.getShopByIdOrThrow(id));

       ProductEntity productEntity = ProductEntity.builder().build();

       productEntity.setShop(shopEntity);

       String name = productDto.getName();

        if(name.trim().isEmpty()){
            throw new BadRequestException("Name cannot be empty");
        }
        shopEntity.getProducts()
                .stream()
                .filter(product -> name.trim().equalsIgnoreCase(product.getName()))
                .findFirst()
                .ifPresent(product -> {
                    throw new BadRequestException("Product already exists");
                });

        productEntity.setName(name);

        Double price = productDto.getPrice();

        if(price.isNaN())
            throw new BadRequestException("Price cannot be NaN");

        productEntity.setPrice(price);

        Optional<String> description = Optional.ofNullable(productDto.getDescription());

        description.ifPresent(productEntity::setDescription);

        //TODO: добавить проверку
        productEntity.setQuantity(productDto.getQuantity());

        productRepository.saveAndFlush(productEntity);
        return productMapper.productEntityToProductDto(productEntity);
    }

    @Transactional
    public List<ProductDto> getAllProductsFromShopById(Long id){
        ShopEntity shopEntity = shopMapper.shopDtoToShopEntity(shopService.getShopByIdOrThrow(id));
//        ShopEntity shopEntity  = shopRepository.findById(id).orElseThrow(()->new BadRequestException("Shop not found"));
        List<ProductEntity> productEntities = shopEntity.getProducts();
        return productEntities
                .stream()
                .map(productMapper::productEntityToProductDto)
                .collect(Collectors.toList());
    }
    public Boolean deleteProductById(Long id){
        getProductByIdOrThrow(id);
        productRepository.deleteById(id);
        return true;
    }

    @Transactional
    public ProductDto updateProductById(Long id, ProductDto productDto) {
        Optional<String> name = Optional.ofNullable(productDto.getName())
                .filter(productName -> !productName.trim().isEmpty());
        Optional<String> description = Optional.ofNullable(productDto.getDescription())
                .filter(productDescription -> !productDescription.trim().isEmpty());
        Optional<Double> price = Optional.ofNullable(productDto.getPrice());

        if(name.isEmpty() && description.isEmpty() && price.isPresent()){
            throw new BadRequestException("Name, description and price cannot be empty");
        }

        ProductEntity productEntity = productRepository.findById(id)
                .orElseThrow(()->new BadRequestException("Product not found"));

       name.ifPresent(productName->{
           getAllProductsFromShopById(productEntity.getShop().getId())
                   .stream()
                .filter(product -> productName.trim().equalsIgnoreCase(product.getName().trim()))
                   .filter(product->!Objects.equals(product.getId(), productEntity.getId()))
                  .findAny()
                   .ifPresent(product -> {
                       throw new BadRequestException("Product already exists");
                   });
                  productEntity.setName(productName);

       });

        description.ifPresent(productEntity::setDescription);
        price.ifPresent(priceProduct ->{
            if(priceProduct == 0)
                throw new BadRequestException("Price cannot be 0");
            productEntity.setPrice(priceProduct);
        });
        productEntity.setUpdatedAt(Instant.now());
        productRepository.saveAndFlush(productEntity);
        return productMapper.productEntityToProductDto(productEntity);

    }

    @Transactional
    public boolean existById(Long id){
        return productRepository.existsById(id);
    }

    @Transactional
    public ProductDto changeQuantity(Long id, Integer quantity) {
        ProductEntity product = productRepository.findById(id).
                orElseThrow(()->new BadRequestException("Product not found"));
        Integer oldQuantity = product.getQuantity();
        if(quantity == 0)
            return productMapper.productEntityToProductDto(product);

        if(quantity < 0){
            if(Math.abs(quantity) > oldQuantity)
                throw new BadRequestException("There is no such quantity");
        }
        product.setQuantity(oldQuantity + quantity);
        productRepository.saveAndFlush(product);
        return productMapper.productEntityToProductDto(product);
    }

}

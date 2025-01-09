package ru.almaz.userservice.service;

import com.thoughtworks.xstream.core.SecurityUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.almaz.userservice.client.CatalogServiceClient;
import ru.almaz.userservice.dto.ProductDto;
import ru.almaz.userservice.entity.Cart;
import ru.almaz.userservice.repository.CartRepository;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository CartRepository;

    private final CatalogServiceClient catalogServiceClient;

    private final UserService userService;


    @Transactional
    public ProductDto addProductToCart(Long productId, Integer quantity) {
        Long userId = Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        catalogServiceClient.exist(productId);
        ProductDto productDto = catalogServiceClient.changeQuantity(productId,quantity);
        Cart cart = new Cart();
        cart.setUser(userService.getUserById(userId));
        cart.setProductId(productId);
        cart.setQuantity(quantity);
        CartRepository.save(cart);
        return productDto;

    }

}

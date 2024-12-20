package ru.almaz.catalogservice.сontroller;

import ru.almaz.catalogservice.dto.ShopDto;
import ru.almaz.catalogservice.service.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class ShopController {
    private final ShopService shopService;

    @PostMapping("api/shops")
    public ShopDto addShop(@RequestParam String name, @RequestParam(required = false) String description) {
        return shopService.addShop(name, Optional.ofNullable(description));

    }
    @GetMapping("api/shops")
    public List<ShopDto> getAllShops() {
        List<ShopDto> shops = shopService.getAllShops();
        return shopService.getAllShops();
    }
    @PatchMapping("api/shops/{shop_id}")
    public ShopDto updateShopName(@PathVariable(name = "shop_id") Long shopId,
                                  @RequestParam(required = false) String name,
                                  @RequestParam(required = false) String description) {
        return shopService.updateShop(shopId, Optional.ofNullable(name), Optional.ofNullable(description));
    }
    @DeleteMapping("/api/shops/{shop_id}")
    public Boolean deleteShop(@PathVariable(name = "shop_id") Long shopId) {
        return shopService.deleteShop(shopId);
    }



}

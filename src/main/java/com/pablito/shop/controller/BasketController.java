package com.pablito.shop.controller;

import com.pablito.shop.domain.dao.Product;
import com.pablito.shop.domain.dto.BasketDto;
import com.pablito.shop.domain.dto.ProductDto;
import com.pablito.shop.mapper.ProductMapper;
import com.pablito.shop.service.BasketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@PreAuthorize("isAuthenticated()")
@RestController
@RequestMapping("/api/v1/baskets")
@RequiredArgsConstructor
public class BasketController {

    private final BasketService basketService;
    private final ProductMapper productMapper;

    @PostMapping
    @Operation(security = @SecurityRequirement(name = "BEARER AUT TOKEN")) //description
    public void addProductToBasket(@RequestBody BasketDto basketDto){
        basketService.addToBasket(basketDto.getProductId(), basketDto.getQuantity());
    }

    @GetMapping
    public List<ProductDto> getAllProductsFromBasket() {
        return productMapper.toDtoList(basketService.getAllProductsFromBasket());
    }

    @DeleteMapping("{productId}")
    public void deleteFromBasket(@PathVariable Long productId) {
        basketService.deleteFromBasket(productId);
    }

    @DeleteMapping
    public void clearBasket(){
        basketService.clearBasket();
    }

    @PutMapping
    public void overrideProductQuantity(@RequestBody BasketDto basketDto) {
        basketService.overrideProductQuantity(basketDto.getProductId(), basketDto.getQuantity());
    }
}

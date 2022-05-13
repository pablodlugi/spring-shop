package com.pablito.shop.service;

import com.pablito.shop.domain.dao.Product;

import java.util.List;

public interface BasketService {

    void addToBasket(Long productId, Long quantity);

    void overrideProductQuantity(Long productId, Long quantity);

    void deleteFromBasket(Long productId);

    List<Product> getAllProductsFromBasket();

    void clearBasket();

}

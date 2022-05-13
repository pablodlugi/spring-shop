package com.pablito.shop.service.impl;

import com.pablito.shop.domain.dao.Basket;
import com.pablito.shop.domain.dao.Product;
import com.pablito.shop.domain.dao.User;
import com.pablito.shop.exception.QuantityExceededException;
import com.pablito.shop.repository.BasketRepository;
import com.pablito.shop.service.BasketService;
import com.pablito.shop.service.ProductService;
import com.pablito.shop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BasketServiceImpl implements BasketService {

    private final BasketRepository basketRepository;
    private final UserService userService;
    private final ProductService productService;

    @Override
    public void addToBasket(Long productId, Long quantity) {
        var user = userService.getCurrentUser();
        basketRepository.findByUserIdAndProductId(user.getId(), productId).ifPresentOrElse(basket -> {

            var product = basket.getProduct();

            if (product.getQuantity() < quantity + basket.getQuantity()) {
                throw new QuantityExceededException("Not enough quantity of product: " + product.getName());
            }

            basket.setQuantity(basket.getQuantity() + quantity);

            basketRepository.save(basket);

        }, addProductToBasket(productId, quantity, user));
    }

    @Transactional
    @Override
    public void overrideProductQuantity(Long productId, Long quantity) {
        var user = userService.getCurrentUser();
        basketRepository.findByUserIdAndProductId(user.getId(), productId).ifPresentOrElse(basket -> {

            var product = basket.getProduct();

            if (product.getQuantity() < quantity) {
                throw new QuantityExceededException("Not enough quantity of product: " + product.getName());
            }

            basket.setQuantity(quantity);

        }, addProductToBasket(productId, quantity, user));
    }

    @Transactional
    @Override
    public void deleteFromBasket(Long productId) {
        var user = userService.getCurrentUser();
        basketRepository.deleteByUserIdAndProductId(user.getId(), productId);
    }

    @Override
    public List<Product> getAllProductsFromBasket() {
        var user = userService.getCurrentUser();
        var basketEntries = basketRepository.findByUserId(user.getId());

        return basketEntries.stream()
                .map(basket -> {
                    var product = basket.getProduct();
                    product.setQuantity(basket.getQuantity());
                    return product;
                })
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void clearBasket() {
        var user = userService.getCurrentUser();
        basketRepository.deleteByUserId(user.getId());
    }

    private Runnable addProductToBasket(Long productId, Long quantity, User user) {
        return () -> {
            var product = productService.getProductById(productId);

            if (product.getQuantity() < quantity) {
                throw new QuantityExceededException("Not enough quantity of product: " + product.getName());
            }

            var basket = Basket.builder()
                    .quantity(quantity)
                    .user(user)
                    .product(product)
                    .build();

            basketRepository.save(basket);
        };
    }
}

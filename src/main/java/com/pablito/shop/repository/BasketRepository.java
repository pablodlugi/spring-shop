package com.pablito.shop.repository;

import com.pablito.shop.domain.dao.Basket;
import com.pablito.shop.domain.dao.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BasketRepository extends JpaRepository<Basket, Long> {
    Optional<Basket> findByUserIdAndProductId(Long userId, Long productId);

    void deleteByUserIdAndProductId(Long userId, Long productId);

    void deleteByUserId(Long userId);

    List<Basket> findByUserId(Long userId);
}

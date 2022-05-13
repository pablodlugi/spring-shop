package com.pablito.shop.controller;

import com.pablito.shop.domain.dto.ProductDto;
import com.pablito.shop.domain.dto.UserDto;
import com.pablito.shop.mapper.HistoryMapper;
import com.pablito.shop.repository.ProductRepository;
import com.pablito.shop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/histories")
@RequiredArgsConstructor
public class HistoryController {
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final HistoryMapper historyMapper;

    @GetMapping("/users/{id}")
    @PreAuthorize("isAuthenticated() && hasRole('ADMIN')")
    public Page<UserDto> getUserHistoryById(@PathVariable Long id, @RequestParam int page, @RequestParam int size){
        return userRepository.findRevisions(id, PageRequest.of(page, size)).map(historyMapper::mapToUserDto);
    }

    @GetMapping("/products/{id}")
    @PreAuthorize("isAuthenticated() && hasRole('ADMIN')")
    public Page<ProductDto> getProductHistoryById(@PathVariable Long id, @RequestParam int page, @RequestParam int size) {
        return productRepository.findRevisions(id, PageRequest.of(page, size)).map(historyMapper::mapToProductDto);
    }
}

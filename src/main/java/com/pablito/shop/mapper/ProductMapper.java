package com.pablito.shop.mapper;

import com.pablito.shop.domain.dao.Product;
import com.pablito.shop.domain.dto.ProductDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper extends AuditableMapper<Product, ProductDto> {
    Product toDao(ProductDto productDto);

    ProductDto toDto(Product product);

    List<ProductDto> toDtoList(List<Product> products);
}

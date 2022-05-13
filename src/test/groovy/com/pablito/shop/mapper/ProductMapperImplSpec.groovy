package com.pablito.shop.mapper

import com.pablito.shop.domain.dao.Product
import com.pablito.shop.domain.dto.ProductDto
import com.pablito.shop.mapper.ProductMapperImpl
import spock.lang.Specification

class ProductMapperImplSpec extends Specification {

    def productMapper = new ProductMapperImpl()

    def 'should map product to productDto'() {

        when:
        def result = productMapper.toDto(product)

        then:
        result == expected

        where:
        product                                                                        || expected
        new Product(id: 1, name: 'pomidor', code: 'XYZ', price: 19.99, quantity: 100L) || new ProductDto(id: 1, name: 'pomidor', code: 'XYZ', price: 19.99, quantity: 100L)
        null                                                                           || null
    }

    def 'should map productDto to product'() {

        when:
        def result = productMapper.toDao(productDto)

        then:
        result == expected

        where:
        productDto                                                                        || expected
        new ProductDto(id: 1, name: 'pomidor', code: 'XYZ', price: 19.99, quantity: 100L) || new Product(id: 1, name: 'pomidor', code: 'XYZ', price: 19.99, quantity: 100L)
        null                                                                              || null
    }
}

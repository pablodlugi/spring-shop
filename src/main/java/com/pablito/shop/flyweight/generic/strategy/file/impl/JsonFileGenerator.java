package com.pablito.shop.flyweight.generic.strategy.file.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pablito.shop.exception.FileCreateException;
import com.pablito.shop.flyweight.generic.strategy.file.FileGeneratorStrategy;
import com.pablito.shop.flyweight.model.FileType;
import com.pablito.shop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class JsonFileGenerator implements FileGeneratorStrategy {

    private final ProductRepository productRepository;
    private final ObjectMapper objectMapper;

    @Override
    public FileType getType() {
        return FileType.JSON;
    }

    @Override
    public byte[] generateFile() {
        log.info("JSON file is generating (generic)");
        try {
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsBytes(productRepository.findAll());
        } catch (JsonProcessingException e) {
            log.error("Error during creating JSON file", e);
        }

        throw new FileCreateException("JSON creation error");
    }
}

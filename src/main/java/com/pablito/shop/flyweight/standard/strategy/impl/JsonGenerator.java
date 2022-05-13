package com.pablito.shop.flyweight.standard.strategy.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pablito.shop.exception.FileCreateException;
import com.pablito.shop.flyweight.model.FileType;
import com.pablito.shop.flyweight.standard.strategy.GeneratorStrategy;
import com.pablito.shop.repository.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


@Component
@Slf4j

public class JsonGenerator implements GeneratorStrategy {

    @Override
    public FileType getType() {
        return FileType.JSON;
    }

    @Override
    public byte[] generateFile() {
        log.info("JSON file is generating");
        throw new FileCreateException("JSON creation error");
    }
}

package com.pablito.shop.flyweight.standard.strategy.impl;

import com.pablito.shop.flyweight.model.FileType;
import com.pablito.shop.flyweight.standard.strategy.GeneratorStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CsvGenerator implements GeneratorStrategy {
    @Override
    public FileType getType() {
        return FileType.CSV;
    }

    @Override
    public byte[] generateFile() {
        log.info("CSV file is generating");
        return new byte[0];
    }
}

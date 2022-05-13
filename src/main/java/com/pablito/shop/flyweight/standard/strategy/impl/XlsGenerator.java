package com.pablito.shop.flyweight.standard.strategy.impl;

import com.pablito.shop.flyweight.model.FileType;
import com.pablito.shop.flyweight.standard.strategy.GeneratorStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class XlsGenerator implements GeneratorStrategy {
    @Override
    public FileType getType() {
        return FileType.XLS;
    }

    @Override
    public byte[] generateFile() {
        log.info("XLS file is generating");
        return new byte[0];
    }
}

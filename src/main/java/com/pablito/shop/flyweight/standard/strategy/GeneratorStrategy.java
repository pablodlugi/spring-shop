package com.pablito.shop.flyweight.standard.strategy;

import com.pablito.shop.flyweight.model.FileType;

public interface GeneratorStrategy {

    FileType getType();

    byte[] generateFile();
}

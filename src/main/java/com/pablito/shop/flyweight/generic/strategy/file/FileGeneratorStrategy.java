package com.pablito.shop.flyweight.generic.strategy.file;

import com.pablito.shop.flyweight.generic.strategy.GenericStrategy;
import com.pablito.shop.flyweight.model.FileType;

public interface FileGeneratorStrategy extends GenericStrategy<FileType> {
    byte[] generateFile();
}

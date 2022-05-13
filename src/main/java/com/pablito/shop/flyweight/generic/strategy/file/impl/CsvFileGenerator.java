package com.pablito.shop.flyweight.generic.strategy.file.impl;

import com.opencsv.CSVWriter;
import com.pablito.shop.domain.dao.Product;
import com.pablito.shop.flyweight.generic.strategy.file.FileGeneratorStrategy;
import com.pablito.shop.flyweight.model.FileType;
import com.pablito.shop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class CsvFileGenerator implements FileGeneratorStrategy {

    private final ProductRepository productRepository;

    @Override
    public FileType getType() {
        return FileType.CSV;
    }

    @Override
    public byte[] generateFile() {
        log.info("CSV file is generating (generic)");

        StringWriter stringWriter = new StringWriter();
        CSVWriter csvWriter = new CSVWriter(stringWriter);
        String[] headers = {"ID", "NAME", "CODE", "PRICE", "QUANTITY"};
        csvWriter.writeNext(headers);

        List<Product> productList = productRepository.findAll();

        for (Product product : productList) {
            csvWriter.writeNext(new String[] {
                    product.getId().toString(),
                    product.getName(),
                    product.getCode(),
                    product.getPrice().toString(),
                    product.getQuantity().toString()
            });
        }

        return stringWriter.toString().getBytes(StandardCharsets.UTF_8);
    }
}

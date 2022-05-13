package com.pablito.shop.flyweight.generic.strategy.file.impl;

import com.pablito.shop.domain.dao.Product;
import com.pablito.shop.flyweight.generic.strategy.file.FileGeneratorStrategy;
import com.pablito.shop.flyweight.model.FileType;
import com.pablito.shop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class XlsFileGenerator implements FileGeneratorStrategy {

    private final ProductRepository productRepository;

    @Override
    public FileType getType() {
        return FileType.XLS;
    }

    @Override
    public byte[] generateFile() {
        log.info("XLS file is generating (generic)");
        int rowIndex = 0;
        try (var workbook = WorkbookFactory.create(false)) { //interfejs autocloseable (closable)

            var sheet = workbook.createSheet("PRODUCT REPORT");
            var headLine = sheet.createRow(rowIndex);
            headLine.createCell(0).setCellValue("ID");
            headLine.createCell(1).setCellValue("NAME");
            headLine.createCell(2).setCellValue("CODE");
            headLine.createCell(3).setCellValue("PRICE");
            headLine.createCell(4).setCellValue("QUANTITY");

            var all = productRepository.findAll();
            for (Product product : all) {
                //++rowIndex - inkrementuje i zwraca
                //rowIndex++ - zwraca i inkrementuje
                var row = sheet.createRow(++rowIndex);
                row.createCell(0).setCellValue(product.getId());
                row.createCell(1).setCellValue(product.getName());
                row.createCell(2).setCellValue(product.getCode());
                row.createCell(3).setCellValue(product.getPrice());
                row.createCell(4).setCellValue(product.getQuantity());
            }
            sheet.setAutoFilter(new CellRangeAddress(0, all.size(), 0, 4));
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            workbook.write(byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            log.error("Error during creating XLS file", e);
        }
        return new byte[0];
    }
}

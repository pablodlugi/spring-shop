package com.pablito.shop.flyweight.generic.strategy.file.impl;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Table;
import com.pablito.shop.domain.dao.Product;
import com.pablito.shop.flyweight.generic.strategy.file.FileGeneratorStrategy;
import com.pablito.shop.flyweight.model.FileType;
import com.pablito.shop.repository.ProductRepository;
import com.pablito.shop.security.SecurityUtils;
import com.pablito.shop.service.MailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.util.Collections;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class PdfFileGenerator implements FileGeneratorStrategy {

    private final ProductRepository productRepository;
    private final MailService mailService;

    @Override
    public FileType getType() {
        return FileType.PDF;
    }

    @Override
    public byte[] generateFile() {
        log.info("PDF file is generating (generic)");

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Document document = new Document(new PdfDocument(new PdfWriter(byteArrayOutputStream)));
        Table table = new Table(5);
        table.addHeaderCell("ID");
        table.addHeaderCell("NAME");
        table.addHeaderCell("CODE");
        table.addHeaderCell("PRICE");
        table.addHeaderCell("QUANTITY");

        List<Product> productList = productRepository.findAll();

        for (Product product : productList) {
            table.addCell(product.getId().toString());
            table.addCell(product.getName());
            table.addCell(product.getCode());
            table.addCell(product.getPrice().toString());
            table.addCell(product.getQuantity().toString());
        }

        document.add(table);
        document.close();

        byte[] file = byteArrayOutputStream.toByteArray();


        mailService.send(SecurityUtils.getCurrentUserEmail(), "PRODUCT REPORT", Collections.emptyMap(), "productRaport.pdf", file);
        return file;
    }
}

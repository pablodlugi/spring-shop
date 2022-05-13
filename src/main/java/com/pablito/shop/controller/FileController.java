package com.pablito.shop.controller;

import com.pablito.shop.flyweight.generic.GenericFactory;
import com.pablito.shop.flyweight.generic.strategy.file.FileGeneratorStrategy;
import com.pablito.shop.flyweight.model.FileType;
import com.pablito.shop.flyweight.standard.GeneratorFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/files")
@RequiredArgsConstructor
public class FileController {

    private final GeneratorFactory generatorFactory;
    private final GenericFactory<FileType, FileGeneratorStrategy> genericFactory;

    @GetMapping
    public void generateFile(@RequestParam FileType fileType) {
        generatorFactory.getStrategy(fileType).generateFile();
    }

    @GetMapping("/generic")
    public ResponseEntity<byte[]> generateGenericFile(@RequestParam FileType fileType) {
        byte[] file = genericFactory.getStrategy(fileType).generateFile();
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);
        headers.set(HttpHeaders.CONTENT_LENGTH, String.valueOf(file.length));
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=report." + fileType.name().toLowerCase());
        return ResponseEntity.ok().headers(headers).body(file);
    }
}

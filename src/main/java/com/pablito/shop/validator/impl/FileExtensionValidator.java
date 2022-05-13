package com.pablito.shop.validator.impl;

import com.pablito.shop.validator.FileExtensionValid;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FileExtensionValidator implements ConstraintValidator<FileExtensionValid, MultipartFile> {
    @Override
    public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext constraintValidatorContext) {
        return multipartFile == null || multipartFile.getOriginalFilename().toLowerCase().endsWith(".png")
                || multipartFile.getOriginalFilename().toLowerCase().endsWith(".jpg");
    }
}

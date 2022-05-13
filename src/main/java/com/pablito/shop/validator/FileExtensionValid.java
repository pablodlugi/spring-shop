package com.pablito.shop.validator;

import com.pablito.shop.validator.impl.FileExtensionValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FileExtensionValidator.class)
public @interface FileExtensionValid {

    String message() default "File extension not supported";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}

package com.pablito.shop.validator;

import com.pablito.shop.validator.impl.PasswordValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE) //adnotacja bedzie wyłącznie dostępna dla typu/klasy
@Retention(RetentionPolicy.RUNTIME) //w trakcie dzialania programu adnotacja bedzie wykonywana
@Constraint(validatedBy = PasswordValidator.class)
public @interface PasswordValid {
    String message() default "Passwords should be the same";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

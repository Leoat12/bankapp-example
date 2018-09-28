package com.leoat12.example.bankapp.validation.annotation;

import com.leoat12.example.bankapp.validation.validator.NegativeAmountValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = NegativeAmountValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface NegativeAmount {
    String message() default "Amount cannot be negative on the moment of the account creation";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

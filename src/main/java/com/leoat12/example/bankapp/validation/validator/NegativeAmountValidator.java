package com.leoat12.example.bankapp.validation.validator;

import com.leoat12.example.bankapp.validation.annotation.NegativeAmount;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NegativeAmountValidator implements ConstraintValidator<NegativeAmount, Double> {

    @Override
    public void initialize(NegativeAmount constraintAnnotation) {

    }

    @Override
    public boolean isValid(Double value, ConstraintValidatorContext context) {
        return value >= 0;
    }
}

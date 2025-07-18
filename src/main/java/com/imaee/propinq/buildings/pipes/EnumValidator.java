package com.imaee.propinq.buildings.pipes;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import static java.util.Arrays.stream;

public class EnumValidator implements ConstraintValidator<ValidEnum, String> {

    private Class<? extends Enum<?>> enumClass;

    @Override
    public void initialize(ValidEnum annotation) {
        this.enumClass = annotation.enumClass();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) return true;
        return stream(enumClass.getEnumConstants())
                .anyMatch(enumConstant -> enumConstant.name().equals(value.toUpperCase()));
    }
}

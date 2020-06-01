package com.zanitech.danielius.validations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

public class PropertyTypeValidator implements ConstraintValidator<PropertyType, String> {
    List<String> propertyTypes = Arrays.asList("apartment", "house", "commercial", "industrial");

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return propertyTypes.contains(value);
    }
}

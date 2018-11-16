package com.dz147.controller;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/*
* 自定义注解
* */
@Target({java.lang.annotation.ElementType.METHOD, java.lang.annotation.ElementType.FIELD, java.lang.annotation.ElementType.ANNOTATION_TYPE, java.lang.annotation.ElementType.CONSTRUCTOR, java.lang.annotation.ElementType.PARAMETER, java.lang.annotation.ElementType.TYPE_USE})
@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@Constraint(validatedBy = LengthValidator.class)
public @interface Lengths {
    String name() default "";

    String message() default "长度(1-7个字符)";

    Class<?>[] groups() default {};

    Class<? extends javax.validation.Payload>[] payload() default {};
}

class LengthValidator implements ConstraintValidator<Lengths, String> {
    @Override
    public void initialize(Lengths constraintAnnotation) {

    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return s.length() >= 1 && s.length() <= 7;
    }
}

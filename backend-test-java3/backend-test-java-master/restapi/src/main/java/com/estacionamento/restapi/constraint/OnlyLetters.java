package com.estacionamento.restapi.constraint;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = OnlyLettersValidator.class)
@Documented
public @interface OnlyLetters {
    String message() default "String must have only letters.";
    Class<?>[] groups() default {};
    Class<?extends Payload> [] payload() default {};
}

class OnlyLettersValidator implements ConstraintValidator<OnlyLetters, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        for (int i = 0; i < value.length(); i++) {
            if (!Character.isLetter(value.charAt(i))) return false;
        }
        return true;
    }
}
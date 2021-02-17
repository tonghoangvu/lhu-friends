package com.tonghoangvu.lhufriends.util.validator;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.annotation.*;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NullOrNotEmptyValidator.class)
public @interface NullOrNotEmpty {
    @NotNull String message() default "{javax.validation.constraints.Pattern.message}";
    @NotNull Class<?> @NotNull [] groups() default {};
}

class NullOrNotEmptyValidator implements ConstraintValidator<NullOrNotEmpty, String> {
    public void initialize(NullOrNotEmpty parameters) {}

    public boolean isValid(
            @Nullable String value, ConstraintValidatorContext constraintValidatorContext) {
        return value == null || !value.isEmpty();
    }
}

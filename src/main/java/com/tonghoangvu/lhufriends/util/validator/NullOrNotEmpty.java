package com.tonghoangvu.lhufriends.util.validator;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = NullOrNotEmptyValidator.class)
public @interface NullOrNotEmpty {
    String message() default "{ javax.validation.constraints.Pattern.message }";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default {};
}

class NullOrNotEmptyValidator implements ConstraintValidator<NullOrNotEmpty, String> {
    public void initialize(NullOrNotEmpty parameters) {}

    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        return value == null || !value.isEmpty();
    }
}

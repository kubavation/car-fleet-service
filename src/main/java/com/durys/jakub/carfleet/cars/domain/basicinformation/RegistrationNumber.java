package com.durys.jakub.carfleet.cars.domain.basicinformation;

import com.durys.jakub.carfleet.common.errors.ValidationError;
import com.durys.jakub.carfleet.common.errors.ValidationErrorHandler;
import com.durys.jakub.carfleet.common.errors.ValidationErrorHandlers;

import java.util.Objects;

public class RegistrationNumber {

    private final String value;

    public RegistrationNumber(String value) {
        this.value = value;
        test(value, ValidationErrorHandlers.throwingValidationErrorHandler());
    }

    static void test(String number, ValidationErrorHandler handler) {

        if (Objects.isNull(number)) {
            handler.handle(new ValidationError("Registration number cannot be empty"));
        }

        //todo additional validation
    }

    public String value() {
        return value;
    }
}

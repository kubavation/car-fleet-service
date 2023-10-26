package com.durys.jakub.carfleet.cars.domain.basicinformation;

import com.durys.jakub.carfleet.common.errors.ValidationError;
import com.durys.jakub.carfleet.common.errors.ValidationErrorHandler;
import com.durys.jakub.carfleet.common.errors.ValidationErrorHandlers;

import java.util.Objects;

public class Vin {

    private final String value;

    public Vin(String value) {
        test(value, ValidationErrorHandlers.throwingValidationErrorHandler());
        this.value = value;
    }

    public Vin(String value, ValidationErrorHandler handler) {
        test(value, handler);
        this.value = value;
    }

    static void test(String number, ValidationErrorHandler handler) {

        if (Objects.isNull(number)) {
            handler.handle(new ValidationError("VIN number cannot be empty"));
        }

        //todo additional validation
    }

    public String value() {
        return value;
    }
}

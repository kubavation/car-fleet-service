package com.durys.jakub.carfleet.cars.domain.tenchicalinspection;

import com.durys.jakub.carfleet.common.errors.ValidationError;
import com.durys.jakub.carfleet.common.errors.ValidationErrorHandler;
import com.durys.jakub.carfleet.common.errors.ValidationErrorHandlers;

import java.math.BigDecimal;
import java.util.Objects;

class Mileage {

    private final BigDecimal value;

    public Mileage(BigDecimal value) {
        this(value, ValidationErrorHandlers.throwingValidationErrorHandler());
    }

    public Mileage(BigDecimal value, ValidationErrorHandler errorHandler) {
        this.value = value;
        test(value, errorHandler);
    }

    static void test(BigDecimal value, ValidationErrorHandler handler) {

        if (Objects.isNull(value)) {
            handler.handle(new ValidationError("Mileage value cannot be empty"));
        }

        if (Objects.nonNull(value) && value.compareTo(BigDecimal.ZERO) < 0) {
            handler.handle(new ValidationError("Mileage value cannot be less than 0"));
        }

    }

    public BigDecimal value() {
        return value;
    }

}

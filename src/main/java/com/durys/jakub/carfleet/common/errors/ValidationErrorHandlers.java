package com.durys.jakub.carfleet.common.errors;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ValidationErrorHandlers {

    public static ThrowingValidationErrorHandler throwingValidationErrorHandler() {
        return new ThrowingValidationErrorHandler();
    }

    public static AggregatingValidationErrorHandler aggregatingValidationErrorHandler() {
        return new AggregatingValidationErrorHandler();
    }
}

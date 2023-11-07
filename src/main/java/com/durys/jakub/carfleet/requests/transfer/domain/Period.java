package com.durys.jakub.carfleet.requests.transfer.domain;

import com.durys.jakub.carfleet.common.errors.ValidationError;
import com.durys.jakub.carfleet.common.errors.ValidationErrorHandler;
import com.durys.jakub.carfleet.common.errors.ValidationErrorHandlers;

import java.time.LocalDateTime;
import java.util.Objects;

class Period {

    private final LocalDateTime from;
    private final LocalDateTime to;

    Period(LocalDateTime from, LocalDateTime to, ValidationErrorHandler handler) {
        this.from = from;
        this.to = to;
        test(from, to, handler);
    }

    Period(LocalDateTime from, LocalDateTime to) {
        this.from = from;
        this.to = to;
        test(from, to, ValidationErrorHandlers.throwingValidationErrorHandler());
    }

    LocalDateTime from() {
        return from;
    }

    LocalDateTime to() {
        return to;
    }

    static void test(LocalDateTime from, LocalDateTime to, ValidationErrorHandler handler) {

        if (Objects.isNull(from)) {
            handler.handle(new ValidationError("Date from cannot be empty"));
            return;
        }

        if (Objects.isNull(to)) {
            handler.handle(new ValidationError("Date to cannot be empty"));
            return;
        }

        if (!from.isBefore(to)) {
            handler.handle(new ValidationError("Date to must be less than date from"));
        }

    }

}

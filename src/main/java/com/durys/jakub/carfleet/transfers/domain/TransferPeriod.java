package com.durys.jakub.carfleet.transfers.domain;

import com.durys.jakub.carfleet.common.errors.ValidationError;
import com.durys.jakub.carfleet.common.errors.ValidationErrorHandler;
import com.durys.jakub.carfleet.common.errors.ValidationErrorHandlers;

import java.time.LocalDateTime;
import java.util.Objects;

class TransferPeriod {

    private final LocalDateTime from;
    private final LocalDateTime to;

    TransferPeriod(LocalDateTime from, LocalDateTime to) {
        this(from, to, ValidationErrorHandlers.throwingValidationErrorHandler());
    }

    TransferPeriod(LocalDateTime from, LocalDateTime to, ValidationErrorHandler errorHandler) {
        test(from, to, errorHandler);
        this.from = from;
        this.to = to;
    }

    static void test(LocalDateTime from, LocalDateTime to, ValidationErrorHandler handler) {
        if (Objects.isNull(from)) {
            handler.handle(new ValidationError("Date from in transfer period cannot be empty"));
        }

        if (Objects.isNull(to)) {
            handler.handle(new ValidationError("Date to in transfer period cannot be empty"));
        }

        if (Objects.nonNull(from) && Objects.nonNull(to) && from.isAfter(to)) {
            handler.handle(new ValidationError("Invalid dates in transfer period"));
        }
    }

}

package com.durys.jakub.carfleet.transfers.domain;

import com.durys.jakub.carfleet.common.errors.ValidationError;
import com.durys.jakub.carfleet.common.errors.ValidationErrorHandler;
import com.durys.jakub.carfleet.common.errors.ValidationErrorHandlers;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
class TransferPeriod {

    private final LocalDateTime from;
    private final LocalDateTime to;

    TransferPeriod(LocalDateTime from, LocalDateTime to) {
        test(from, to, ValidationErrorHandlers.throwingValidationErrorHandler());
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

    LocalDateTime from() {
        return from;
    }

    LocalDateTime to() {
        return to;
    }


}

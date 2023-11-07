package com.durys.jakub.carfleet.requests.transfer.domain;

import com.durys.jakub.carfleet.common.errors.ValidationError;
import com.durys.jakub.carfleet.common.errors.ValidationErrorHandler;
import com.durys.jakub.carfleet.common.errors.ValidationErrorHandlers;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
class TransferRoute {

    private final String departure;
    private final String destination;

    TransferRoute(String departure, String destination, ValidationErrorHandler handler) {
        this.departure = departure;
        this.destination = destination;
        test(departure, destination, handler);
    }

    TransferRoute(String departure, String destination) {
        this(departure, destination, ValidationErrorHandlers.throwingValidationErrorHandler());
    }

    static void test(String departure, String destination, ValidationErrorHandler handler) {

        if (Objects.isNull(departure) || departure.trim().isEmpty()) {
            handler.handle(new ValidationError("Departure cannot be empty"));
        }

        if (Objects.isNull(destination) || destination.trim().isEmpty()) {
            handler.handle(new ValidationError("Destination cannot be empty"));
        }
    }

    String departure() {
        return departure;
    }

    String destination() {
        return destination;
    }
}

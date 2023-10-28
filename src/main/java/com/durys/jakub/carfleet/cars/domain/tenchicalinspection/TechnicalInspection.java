package com.durys.jakub.carfleet.cars.domain.tenchicalinspection;

import com.durys.jakub.carfleet.common.errors.ValidationError;
import com.durys.jakub.carfleet.common.errors.ValidationErrorHandler;
import com.durys.jakub.carfleet.common.errors.ValidationErrorHandlers;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Getter
public class TechnicalInspection {

    private final LocalDate at;
    private final String description;
    private final Mileage mileage;
    private final LocalDate nextAt;

    public TechnicalInspection(LocalDate at, String description, BigDecimal mileage, LocalDate nextAt) {
        this(at, description, mileage, nextAt, ValidationErrorHandlers.throwingValidationErrorHandler());
    }

    public TechnicalInspection(LocalDate at, String description, BigDecimal mileage, LocalDate nextAt, ValidationErrorHandler validationErrorHandler) {
        test(at, description, mileage, nextAt, validationErrorHandler);
        this.at = at;
        this.description = description;
        this.mileage = new Mileage(mileage);
        this.nextAt = nextAt;
    }

    static void test(LocalDate at, String description, BigDecimal mileage, LocalDate nextAt, ValidationErrorHandler handler) {

        if (Objects.isNull(at)) {
            handler.handle(new ValidationError("Date of technical inspection cannot be empty"));
        }

        if (StringUtils.isEmpty(description)) {
            handler.handle(new ValidationError("Technical inspection description cannot be empty"));
        }

        Mileage.test(mileage, handler);

        if (Objects.isNull(nextAt)) {
            handler.handle(new ValidationError("Date of next technical inspection cannot be empty"));
            return;
        }

        if (Objects.nonNull(at) && nextAt.isBefore(at)) {
            handler.handle(new ValidationError("Date of next technical inspection cannot be earlier " +
                    "than current technical inspection date"));
        }

    }

    public LocalDate at() {
        return at;
    }

    public LocalDate nextAt() {
        return nextAt;
    }
}

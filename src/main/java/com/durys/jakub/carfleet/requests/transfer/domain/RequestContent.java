package com.durys.jakub.carfleet.requests.transfer.domain;

import com.durys.jakub.carfleet.common.errors.ValidationError;
import com.durys.jakub.carfleet.common.errors.ValidationErrorHandler;
import com.durys.jakub.carfleet.common.errors.ValidationErrorHandlers;
import com.durys.jakub.carfleet.requests.vo.RequestPurpose;
import com.durys.jakub.carfleet.sharedkernel.cars.CarType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
class RequestContent {

    @Embedded
    private final Period period;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "PURPOSE"))
    private final RequestPurpose purpose;

    @Embedded
    private final TransferRoute transferRoute;

    @Enumerated(EnumType.STRING)
    private final CarType carType;

    RequestContent(LocalDateTime from, LocalDateTime to, String purpose,
                   String departure, String destination, CarType carType) {
        this(from, to, purpose, departure, destination, carType, ValidationErrorHandlers.throwingValidationErrorHandler());
    }

    RequestContent(LocalDateTime from, LocalDateTime to, String purpose,
                   String departure, String destination, CarType carType, ValidationErrorHandler handler) {

        this.period = new Period(from, to, handler);
        this.purpose = new RequestPurpose(purpose, handler);
        this.transferRoute = new TransferRoute(departure, destination, handler);
        this.carType = carType;
    }

    public LocalDateTime from() {
        return period.from();
    }

    public LocalDateTime to() {
        return period.to();
    }

    public RequestPurpose purpose() {
        return purpose;
    }

    public String departure() {
        return transferRoute.departure();
    }

    public String destination() {
        return transferRoute.destination();
    }

    public CarType carType() {
        return carType;
    }


    public static void test(LocalDateTime from, LocalDateTime to, String purpose, String departure,
                     String destination, CarType carType,
                     ValidationErrorHandler handler) {

        Period.test(from, to, handler);
        RequestPurpose.test(purpose, handler);
        TransferRoute.test(departure, destination, handler);

        if (Objects.isNull(carType)) {
            handler.handle(new ValidationError("Car type cannot be empty"));
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestContent that = (RequestContent) o;
        return Objects.equals(period, that.period) && Objects.equals(purpose, that.purpose)
                && Objects.equals(transferRoute, that.transferRoute) && carType == that.carType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(period, purpose, transferRoute, carType);
    }
}

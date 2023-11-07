package com.durys.jakub.carfleet.requests.transfer.domain;

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

    private final String departure;
    private final String destination;

    @Enumerated(EnumType.STRING)
    private final CarType carType;

    RequestContent(LocalDateTime from, LocalDateTime to, RequestPurpose purpose,
                   String departure, String destination, CarType carType) {
        this.period = new Period(from, to);
        this.purpose = purpose;
        this.departure = departure;
        this.destination = destination;
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
        return departure;
    }

    public String destination() {
        return destination;
    }

    public CarType carType() {
        return carType;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestContent that = (RequestContent) o;
        return Objects.equals(period, that.period) && Objects.equals(purpose, that.purpose)
                && Objects.equals(departure, that.departure)
                && Objects.equals(destination, that.destination) && carType == that.carType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(period, purpose, departure, destination, carType);
    }
}

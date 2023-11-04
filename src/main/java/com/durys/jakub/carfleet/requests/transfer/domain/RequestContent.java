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

    private final LocalDateTime from;
    private final LocalDateTime to;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "PURPOSE"))
    private final RequestPurpose purpose;
    private final String departure;
    private final String destination;

    @Enumerated(EnumType.STRING)
    private final CarType carType;

    RequestContent(LocalDateTime from, LocalDateTime to, RequestPurpose purpose,
                   String departure, String destination, CarType carType) {
        this.from = from;
        this.to = to;
        this.purpose = purpose;
        this.departure = departure;
        this.destination = destination;
        this.carType = carType;
    }

    public LocalDateTime from() {
        return from;
    }

    public LocalDateTime to() {
        return to;
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
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (RequestContent) obj;
        return Objects.equals(this.from, that.from) &&
                Objects.equals(this.to, that.to) &&
                Objects.equals(this.purpose, that.purpose) &&
                Objects.equals(this.departure, that.departure) &&
                Objects.equals(this.destination, that.destination) &&
                Objects.equals(this.carType, that.carType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to, purpose, departure, destination, carType);
    }


}

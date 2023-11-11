package com.durys.jakub.carfleet.cars.domain;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class CarId implements Serializable {

    private final UUID value;

    public CarId(UUID value) {
        this.value = value;
    }

    public UUID value() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (CarId) obj;
        return Objects.equals(this.value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

}

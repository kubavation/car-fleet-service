package com.durys.jakub.carfleet.drivers.domain;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class DriverId implements Serializable {

    private final UUID value;

    public DriverId(UUID value) {
        this.value = value;
    }

    public UUID value() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (DriverId) obj;
        return Objects.equals(this.value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

}

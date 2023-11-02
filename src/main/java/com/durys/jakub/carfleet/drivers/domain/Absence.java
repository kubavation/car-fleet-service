package com.durys.jakub.carfleet.drivers.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
class Absence {

    @Id
    private final UUID id;
    private final LocalDate date;

    Absence(LocalDate date) {
        this.id = UUID.randomUUID();
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Absence absence = (Absence) o;
        return Objects.equals(id, absence.id) && Objects.equals(date, absence.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date);
    }
}

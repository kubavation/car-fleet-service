package com.durys.jakub.carfleet.drivers.domain;

import java.time.LocalDate;
import java.util.Objects;

class Absence {

    private final LocalDate date;

    Absence(LocalDate date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Absence absence = (Absence) o;
        return Objects.equals(date, absence.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date);
    }
}

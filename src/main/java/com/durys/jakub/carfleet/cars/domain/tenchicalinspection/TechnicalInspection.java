package com.durys.jakub.carfleet.cars.domain.tenchicalinspection;

import java.time.LocalDate;

public class TechnicalInspection {

    private final LocalDate at;
    private final String description;
    private final Mileage mileage;
    private final LocalDate nextAt;

    public TechnicalInspection(LocalDate at, String description, Mileage mileage, LocalDate nextAt) {
        this.at = at;
        this.description = description;
        this.mileage = mileage;
        this.nextAt = nextAt;
    }
}

package com.durys.jakub.carfleet.cars.domain.tenchicalinspection;

import java.time.LocalDate;

public record TechnicalInspection(LocalDate at, String description, Mileage mileage, LocalDate nextAt) {
}

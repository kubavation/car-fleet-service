package com.durys.jakub.carfleet.cars.domain;

import com.durys.jakub.carfleet.cars.domain.basicinformation.CarBasicInformation;
import com.durys.jakub.carfleet.cars.domain.tenchicalinspection.TechnicalInspection;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.Set;

@Getter
public class Car {

    private final CarId carId;
    private final CarType carType;

    private final CarBasicInformation basicInformation;

    private final Set<TechnicalInspection> technicalInspections;

    public Car(CarId carId, CarType carType, CarBasicInformation basicInformation,
               Set<TechnicalInspection> technicalInspections) {
        this.carId = carId;
        this.carType = carType;
        this.basicInformation = basicInformation;
        this.technicalInspections = technicalInspections;
    }

    public LocalDate nextTechnicalInspectionAt() {
        return technicalInspections.stream()
                .max(Comparator.comparing(TechnicalInspection::at))
                .map(TechnicalInspection::nextAt)
                .orElse(null);
    }
}

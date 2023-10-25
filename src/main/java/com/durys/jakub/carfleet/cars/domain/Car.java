package com.durys.jakub.carfleet.cars.domain;

import com.durys.jakub.carfleet.cars.domain.basicinformation.CarBasicInformation;
import com.durys.jakub.carfleet.cars.domain.tenchicalinspection.TechnicalInspection;

import java.util.Set;

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
}

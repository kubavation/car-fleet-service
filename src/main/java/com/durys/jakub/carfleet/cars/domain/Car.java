package com.durys.jakub.carfleet.cars.domain;

import com.durys.jakub.carfleet.cars.domain.basicinformation.CarBasicInformation;
import com.durys.jakub.carfleet.cars.domain.tenchicalinspection.TechnicalInspection;
import com.durys.jakub.carfleet.common.errors.ValidationError;
import com.durys.jakub.carfleet.sharedkernel.cars.CarType;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.Set;

@Getter
public class Car {

    public enum CarStatus {
        Registered,
        Unregistered
    }

    private final CarId carId;
    private final CarType carType;

    private final CarBasicInformation basicInformation;

    private final Set<TechnicalInspection> technicalInspections;

    private CarStatus status;

    public Car(CarId carId, CarType carType, CarBasicInformation basicInformation,
               Set<TechnicalInspection> technicalInspections, CarStatus status) {
        this.carId = carId;
        this.carType = carType;
        this.basicInformation = basicInformation;
        this.technicalInspections = technicalInspections;
        this.status = status;
    }

    public Car(CarId carId, CarType carType, CarBasicInformation basicInformation,
               Set<TechnicalInspection> technicalInspections) {
        this.carId = carId;
        this.carType = carType;
        this.basicInformation = basicInformation;
        this.technicalInspections = technicalInspections;
        this.status = CarStatus.Unregistered;
    }

    public Car unregister() {

        if (status != CarStatus.Registered) {
            throw new ValidationError("Car cannot be unregistered");
        }

        status = CarStatus.Unregistered;
        return this;
    }

    public Car register() {

        if (status != CarStatus.Unregistered) {
            throw new ValidationError("Car cannot be registered");
        }

        status = CarStatus.Registered;
        return this;
    }


    public void undergoTechnicalInspection(TechnicalInspection inspection) {
        technicalInspections.add(inspection);
    }

    public LocalDate nextTechnicalInspectionAt() {
        return technicalInspections.stream()
                .max(Comparator.comparing(TechnicalInspection::at))
                .map(TechnicalInspection::nextAt)
                .orElse(null);
    }

    public CarStatus status() {
        return status;
    }

}

package com.durys.jakub.carfleet.cars.domain;

import com.durys.jakub.carfleet.cars.domain.basicinformation.CarBasicInformation;

public class Car {

    private final CarId carId;
    private final CarType carType;

    private final CarBasicInformation basicInformation;

    public Car(CarId carId, CarType carType, CarBasicInformation basicInformation) {
        this.carId = carId;
        this.carType = carType;
        this.basicInformation = basicInformation;
    }
}

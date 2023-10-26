package com.durys.jakub.carfleet.cars.domain.basicinformation;


public class CarBasicInformation {

    private final RegistrationNumber registrationNumber;
    private final Vin vin;
    private final FuelType fuelType;

    public CarBasicInformation(RegistrationNumber registrationNumber, Vin vin, FuelType fuelType) {
        this.registrationNumber = registrationNumber;
        this.vin = vin;
        this.fuelType = fuelType;
    }
}

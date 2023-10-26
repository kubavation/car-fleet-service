package com.durys.jakub.carfleet.cars.domain.basicinformation;


import com.durys.jakub.carfleet.common.errors.ValidationErrorHandler;
import com.durys.jakub.carfleet.common.errors.ValidationErrorHandlers;

public class CarBasicInformation {

    private final RegistrationNumber registrationNumber;
    private final Vin vin;
    private final FuelType fuelType;

    public CarBasicInformation(RegistrationNumber registrationNumber, Vin vin, FuelType fuelType) {
        this.registrationNumber = registrationNumber;
        this.vin = vin;
        this.fuelType = fuelType;
    }

    public CarBasicInformation(String registrationNumber, String vin, FuelType fuelType, ValidationErrorHandler handler) {
        this.registrationNumber = new RegistrationNumber(registrationNumber, handler);
        this.vin = new Vin(vin, handler);
        this.fuelType = fuelType;
    }

    public CarBasicInformation(String registrationNumber, String vin, FuelType fuelType) {
        this(registrationNumber, vin, fuelType, ValidationErrorHandlers.throwingValidationErrorHandler());
    }

}

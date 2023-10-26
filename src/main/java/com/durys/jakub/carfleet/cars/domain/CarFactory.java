package com.durys.jakub.carfleet.cars.domain;

import com.durys.jakub.carfleet.cars.domain.basicinformation.CarBasicInformation;
import com.durys.jakub.carfleet.cars.domain.basicinformation.FuelType;
import com.durys.jakub.carfleet.common.errors.ValidationErrorHandler;
import com.durys.jakub.carfleet.common.errors.ValidationErrorHandlers;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CarFactory {

    public static CarBuilder withValidationErrorHandler(ValidationErrorHandler handler) {
        return new CarBuilder(handler);
    }


    public static class CarBuilder implements BasicInformationFactory {

        private final ValidationErrorHandler errorHandler;
        private CarBasicInformation carBasicInformation;

        public CarBuilder(ValidationErrorHandler errorHandler) {
            this.errorHandler = errorHandler;
        }

        @Override
        public CarBuilder withBasicInformation(String registrationNumber, String vin, FuelType fuelType) {
            this.carBasicInformation = new CarBasicInformation(registrationNumber, vin, fuelType, errorHandler);
            return this;
        }
    }

    public Car construct() {
        return
    }

    interface BasicInformationFactory {
        CarBuilder withBasicInformation(String registrationNumber, String vin, FuelType fuelType);
    }

}


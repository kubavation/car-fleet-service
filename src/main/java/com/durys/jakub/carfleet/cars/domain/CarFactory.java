package com.durys.jakub.carfleet.cars.domain;

import com.durys.jakub.carfleet.cars.domain.basicinformation.CarBasicInformation;
import com.durys.jakub.carfleet.cars.domain.basicinformation.FuelType;
import com.durys.jakub.carfleet.common.errors.ValidationErrorHandler;
import com.durys.jakub.carfleet.common.errors.ValidationErrorHandlers;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.HashSet;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CarFactory {

    public static InitialFactory withValidationErrorHandler(ValidationErrorHandler handler) {
        return new CarBuilder(handler);
    }


    public static class CarBuilder implements InitialFactory, BasicInformationFactory, FinalStepFactory {

        private final ValidationErrorHandler errorHandler;

        private CarId carId;
        private CarType carType;
        private CarBasicInformation carBasicInformation;


        public CarBuilder(ValidationErrorHandler errorHandler) {
            this.errorHandler = errorHandler;
        }

        @Override
        public BasicInformationFactory with(CarId carId, CarType carType) {
            this.carId = carId;
            this.carType = carType; //todo validation
            return this;
        }

        @Override
        public FinalStepFactory withBasicInformation(String registrationNumber, String vin, FuelType fuelType) {
            this.carBasicInformation = new CarBasicInformation(registrationNumber, vin, fuelType, errorHandler);
            return this;
        }

        @Override
        public Car construct() {
            return new Car(carId, carType, carBasicInformation, new HashSet<>()); //
        }

    }

    interface BasicInformationFactory {
        FinalStepFactory withBasicInformation(String registrationNumber, String vin, FuelType fuelType);
    }

    interface InitialFactory {
        BasicInformationFactory with(CarId carId, CarType carType);
    }

    interface FinalStepFactory {
        Car construct();
    }

}


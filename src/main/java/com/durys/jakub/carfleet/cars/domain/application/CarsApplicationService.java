package com.durys.jakub.carfleet.cars.domain.application;

import com.durys.jakub.carfleet.cars.domain.*;
import com.durys.jakub.carfleet.cars.domain.basicinformation.FuelType;
import com.durys.jakub.carfleet.common.OperationResult;
import com.durys.jakub.carfleet.common.errors.ValidationErrorHandler;
import com.durys.jakub.carfleet.common.errors.ValidationErrorHandlers;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CarsApplicationService {

    private final CarsRepository carsRepository;

    public CarsApplicationService(CarsRepository carsRepository) {
        this.carsRepository = carsRepository;
    }


    public OperationResult register(CarType carType, String registrationNumber, String vin, FuelType fuelType) {

        var validationErrorHandler = ValidationErrorHandlers.aggregatingValidationErrorHandler();

        Car registeredCar = CarFactory
                .withValidationErrorHandler(validationErrorHandler)
                .with(new CarId(UUID.randomUUID()), carType)
                .withBasicInformation(registrationNumber, vin, fuelType)
                .construct();

        carsRepository.save(registeredCar);

        if (validationErrorHandler.hasErrors()) {
            return OperationResult.failure(validationErrorHandler.errorMessages());
        }

        return OperationResult.success();
    }

    public OperationResult unregister(CarId carId) {

        Car car = carsRepository.load(carId)
                .orElseThrow(RuntimeException::new);//todo

        car.unregister();

        carsRepository.save(car);

        return OperationResult.success();
    }
}

package com.durys.jakub.carfleet.cars.infrastructure;

import com.durys.jakub.carfleet.cars.domain.Car;
import com.durys.jakub.carfleet.cars.domain.CarId;
import com.durys.jakub.carfleet.cars.domain.CarsRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MockedCarsRepository implements CarsRepository {

    private static final Map<CarId, Car> DB = new HashMap<>();

    @Override
    public void save(Car car) {
        DB.put(car.getCarId(), car);
    }

    @Override
    public Car load(CarId id) {
        Car car = DB.get(id);

        if (Objects.isNull(car)) {
            throw new RuntimeException("Car not found");
        }

        return car;
    }

}

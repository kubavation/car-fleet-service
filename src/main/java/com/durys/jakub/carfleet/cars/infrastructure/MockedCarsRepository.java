package com.durys.jakub.carfleet.cars.infrastructure;

import com.durys.jakub.carfleet.cars.domain.Car;
import com.durys.jakub.carfleet.cars.domain.CarId;
import com.durys.jakub.carfleet.cars.domain.CarsRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MockedCarsRepository implements CarsRepository {

    private static final Map<CarId, Car> DB = new HashMap<>();

    @Override
    public void save(Car car) {
        DB.put(car.getCarId(), car);
    }

    @Override
    public Optional<Car> load(CarId id) {
        return Optional.ofNullable(DB.get(id));
    }

}

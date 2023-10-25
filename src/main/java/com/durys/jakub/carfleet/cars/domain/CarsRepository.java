package com.durys.jakub.carfleet.cars.domain;

import java.util.Optional;

public interface CarsRepository {
    Optional<Car> load(CarId id);
    void save(Car car);
}

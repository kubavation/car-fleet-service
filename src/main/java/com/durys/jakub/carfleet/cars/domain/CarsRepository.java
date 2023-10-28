package com.durys.jakub.carfleet.cars.domain;

public interface CarsRepository {
    Car load(CarId id);
    void save(Car car);
}

package com.durys.jakub.carfleet.drivers.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface DriverRepository extends CrudRepository<Driver, DriverId> {
    Optional<Driver> find(DriverId driverId);
    List<Driver> findAll();
}

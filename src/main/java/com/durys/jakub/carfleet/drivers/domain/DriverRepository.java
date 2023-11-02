package com.durys.jakub.carfleet.drivers.domain;

import org.springframework.data.repository.CrudRepository;

public interface DriverRepository extends CrudRepository<Driver, DriverId> {
}

package com.durys.jakub.carfleet.drivers;

import java.util.Optional;

public interface DriverRepository {
    Optional<Driver> find(DriverId driverId);
}

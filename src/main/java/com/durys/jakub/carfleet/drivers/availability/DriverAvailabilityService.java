package com.durys.jakub.carfleet.drivers.availability;

import com.durys.jakub.carfleet.drivers.DriverId;

import java.time.LocalDateTime;

public interface DriverAvailabilityService {
    boolean available(DriverId driverId, LocalDateTime from, LocalDateTime to);
}

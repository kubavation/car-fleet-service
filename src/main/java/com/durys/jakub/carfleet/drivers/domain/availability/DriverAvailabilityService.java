package com.durys.jakub.carfleet.drivers.domain.availability;

import com.durys.jakub.carfleet.drivers.domain.DriverId;

import java.time.LocalDateTime;

public interface DriverAvailabilityService {
    boolean available(DriverId driverId, LocalDateTime from, LocalDateTime to);
}

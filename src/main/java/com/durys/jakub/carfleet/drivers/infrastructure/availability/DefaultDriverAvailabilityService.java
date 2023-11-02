package com.durys.jakub.carfleet.drivers.infrastructure.availability;

import com.durys.jakub.carfleet.drivers.domain.DriverId;
import com.durys.jakub.carfleet.drivers.domain.DriverRepository;
import com.durys.jakub.carfleet.drivers.domain.availability.DriverAvailabilityService;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;


@RequiredArgsConstructor
public class DefaultDriverAvailabilityService implements DriverAvailabilityService {

    private final DriverRepository driverRepository;

    @Override
    public boolean available(DriverId driverId, LocalDateTime from, LocalDateTime to) {
        return driverRepository.findById(driverId)
                .map(driver -> driver.activeBetween(from.toLocalDate(), to.toLocalDate()))
                .orElse(false);
    }


}

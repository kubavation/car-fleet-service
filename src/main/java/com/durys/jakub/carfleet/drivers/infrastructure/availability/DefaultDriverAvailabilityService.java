package com.durys.jakub.carfleet.drivers.infrastructure.availability;

import com.durys.jakub.carfleet.drivers.domain.DriverId;
import com.durys.jakub.carfleet.drivers.domain.DriverRepository;
import com.durys.jakub.carfleet.drivers.domain.availability.DriverAvailabilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;


@RequiredArgsConstructor
class DefaultDriverAvailabilityService implements DriverAvailabilityService {

    private final DriverRepository driverRepository;

    @Override
    public boolean available(DriverId driverId, LocalDateTime from, LocalDateTime to) {
        return driverRepository.find(driverId)
                .map(driver -> driver.inactiveBetween(from.toLocalDate(), to.toLocalDate()))
                .orElse(false);
    }


}

package com.durys.jakub.carfleet.drivers.infrastructure.availability;

import com.durys.jakub.carfleet.drivers.domain.DriverId;
import com.durys.jakub.carfleet.drivers.domain.availability.DriverAvailabilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;


@RequiredArgsConstructor
class DefaultDriverAvailabilityService implements DriverAvailabilityService {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public boolean available(DriverId carId, LocalDateTime from, LocalDateTime to) {
        return availableInWork();
    }


    private boolean availableInWork() {
        //cal to external service
        return true;
    }

}

package com.durys.jakub.carfleet.drivers.availability;

import com.durys.jakub.carfleet.drivers.DriverId;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
class DefaultDriverAvailabilityService implements DriverAvailabilityService {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public boolean available(DriverId carId, LocalDateTime from, LocalDateTime to) {
        //todo
        return true;
    }

}

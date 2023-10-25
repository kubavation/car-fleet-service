package com.durys.jakub.carfleet.cars.availability;

import com.durys.jakub.carfleet.cars.domain.CarId;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
class DefaultCarAvailabilityService implements CarAvailabilityService {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public boolean available(CarId carId, LocalDateTime from, LocalDateTime to) {

        return !carInExistingTransfer(carId, from, to) && !needsCarOverview(carId, from, to);

    }


    private boolean carInExistingTransfer(CarId carId, LocalDateTime from, LocalDateTime to) {
        //todo
        return false;
    }

    private boolean needsCarOverview(CarId carId, LocalDateTime from, LocalDateTime to) {
        //todo
        return false;
    }
}

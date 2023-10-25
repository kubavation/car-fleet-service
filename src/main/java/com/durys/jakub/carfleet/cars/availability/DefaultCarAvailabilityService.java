package com.durys.jakub.carfleet.cars.availability;

import com.durys.jakub.carfleet.cars.domain.CarId;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
class DefaultCarAvailabilityService implements CarAvailabilityService {

    @Override
    public boolean available(CarId carId, LocalDateTime from, LocalDateTime to) {
        return false;
    }
}

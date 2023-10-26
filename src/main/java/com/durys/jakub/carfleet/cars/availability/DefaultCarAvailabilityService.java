package com.durys.jakub.carfleet.cars.availability;

import com.durys.jakub.carfleet.cars.domain.Car;
import com.durys.jakub.carfleet.cars.domain.CarId;
import com.durys.jakub.carfleet.cars.domain.CarsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Objects;

@Component
@RequiredArgsConstructor
class DefaultCarAvailabilityService implements CarAvailabilityService {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final CarsRepository carsRepository;

    @Override
    public boolean available(CarId carId, LocalDateTime from, LocalDateTime to) {

        return !carInExistingTransfer(carId, from, to) && !needsTechnicalInspection(carId, from, to);

    }


    private boolean carInExistingTransfer(CarId carId, LocalDateTime from, LocalDateTime to) {
        //todo
        return false;
    }

    private boolean needsTechnicalInspection(CarId carId, LocalDateTime from, LocalDateTime to) {

        Car car = carsRepository.load(carId)
                .orElseThrow(RuntimeException::new);

        if (Objects.isNull(car.nextTechnicalInspectionAt())) {
            return false;
        }

        if (!to.toLocalDate().isBefore(car.nextTechnicalInspectionAt())) {
            return true;
        }

        return false;
    }
}

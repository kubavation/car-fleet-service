package com.durys.jakub.carfleet.drivers.application;

import com.durys.jakub.carfleet.drivers.domain.Driver;
import com.durys.jakub.carfleet.drivers.domain.DriverRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DriversFleetUpdateService {

    private final DriverRepository driverRepository;


    @Transactional
    public void updateDriverFleet(List<Driver> drivers) {
        driverRepository.saveAll(drivers); //todo deletion
    }


}

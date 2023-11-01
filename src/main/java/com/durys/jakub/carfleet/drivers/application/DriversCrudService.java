package com.durys.jakub.carfleet.drivers.application;

import com.durys.jakub.carfleet.drivers.domain.Driver;
import com.durys.jakub.carfleet.drivers.domain.DriverRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DriversCrudService {

    private final DriverRepository driverRepository;


    public void updateDriverFleet(List<Driver> drivers) {
        //todo
    }


}

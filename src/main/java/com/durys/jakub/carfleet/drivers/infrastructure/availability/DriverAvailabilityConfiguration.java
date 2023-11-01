package com.durys.jakub.carfleet.drivers.infrastructure.availability;

import com.durys.jakub.carfleet.drivers.domain.DriverRepository;
import com.durys.jakub.carfleet.drivers.domain.availability.DriverAvailabilityService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class DriverAvailabilityConfiguration {

    @Bean
    DriverAvailabilityService driverAvailabilityService(DriverRepository driverRepository) {
        return new DefaultDriverAvailabilityService(driverRepository);
    }
}

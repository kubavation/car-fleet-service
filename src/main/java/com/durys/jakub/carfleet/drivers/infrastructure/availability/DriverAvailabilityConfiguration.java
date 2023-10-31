package com.durys.jakub.carfleet.drivers.infrastructure.availability;

import com.durys.jakub.carfleet.drivers.domain.availability.DriverAvailabilityService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@Configuration
class DriverAvailabilityConfiguration {

    @Bean
    DriverAvailabilityService driverAvailabilityService(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        return new DefaultDriverAvailabilityService(namedParameterJdbcTemplate);
    }
}

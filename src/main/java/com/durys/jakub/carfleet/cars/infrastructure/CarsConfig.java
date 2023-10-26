package com.durys.jakub.carfleet.cars.infrastructure;

import com.durys.jakub.carfleet.cars.domain.CarsRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CarsConfig {

    @Bean
    public CarsRepository carsRepository() {
        return new MockedCarsRepository();
    }

}

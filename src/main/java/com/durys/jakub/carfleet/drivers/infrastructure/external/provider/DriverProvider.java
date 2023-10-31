package com.durys.jakub.carfleet.drivers.infrastructure.external.provider;

import com.durys.jakub.carfleet.drivers.domain.Driver;
import reactor.core.publisher.Flux;

public interface DriverProvider {
    Flux<Driver> loadAllBy(String link);
}

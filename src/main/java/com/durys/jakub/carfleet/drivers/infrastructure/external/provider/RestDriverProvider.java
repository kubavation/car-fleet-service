package com.durys.jakub.carfleet.drivers.infrastructure.external.provider;

import com.durys.jakub.carfleet.drivers.domain.Driver;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

class RestDriverProvider implements DriverProvider {

    private final WebClient webClient;

    RestDriverProvider(WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public Flux<Driver> loadAllBy(String link) {
        return null; //todo
    }
}

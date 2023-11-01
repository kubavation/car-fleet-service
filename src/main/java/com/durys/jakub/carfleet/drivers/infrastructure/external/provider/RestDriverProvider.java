package com.durys.jakub.carfleet.drivers.infrastructure.external.provider;

import com.durys.jakub.carfleet.drivers.domain.Driver;
import com.durys.jakub.carfleet.drivers.domain.DriverId;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

class RestDriverProvider implements DriverProvider {

    private final WebClient webClient;

    RestDriverProvider(WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public Flux<Driver> loadAllBy(String link) {
        return webClient
                .get()
                .uri(link)
                .retrieve()
                .bodyToFlux(User.class)
                .map(DriverACL::convert);
    }

    static class DriverACL {
        public static Driver convert(User user) {
            return new Driver(
                    new DriverId(user.userId().value()), user.firstName(), user.lastName());
        }
    }
}

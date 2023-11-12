package com.durys.jakub.carfleet.plannedevent.infrastructure;

import com.durys.jakub.carfleet.plannedevent.domain.PlannedEventRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
class PlannedEventConfiguration {


    @Bean
    PlannedEventRepository plannedEventRepository(WebClient.Builder webClientBuilder,
                                                  @Value("${planned-events-service.url}") String baseUrl) {
        return new RestPlannedEventRepository(webClientBuilder.baseUrl(baseUrl).build());
    }

}

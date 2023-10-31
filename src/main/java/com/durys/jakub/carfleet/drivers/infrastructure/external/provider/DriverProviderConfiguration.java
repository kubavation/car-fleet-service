package com.durys.jakub.carfleet.drivers.infrastructure.external.provider;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;

@Configuration
class DriverProviderConfiguration {

    @Bean
    @LoadBalanced
    WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }

    @Bean
    WebClient driverProviderWebClient(WebClient.Builder builder, @Value("${am-service-url}") String baseUrl) {
        return builder
                .baseUrl(baseUrl).build();
    }

    @Bean
    DriverProvider driverProvider(WebClient webClient) {
        return new RestDriverProvider(webClient);
    }
}

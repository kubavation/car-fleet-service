package com.durys.jakub.carfleet.sharedkernel.identity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

@Configuration
class IdentityConfiguration {

    @Bean
    IdentityProvider<UUID> identityProvider() {
        return new UUIDIdentityProvider();
    }
}

package com.durys.jakub.carfleet.transfers.infrastructure;

import com.durys.jakub.carfleet.transfers.domain.TransferRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class TransferConfiguration {

    @Bean
    TransferRepository transferRepository() {
        return new InMemoryTransferRepository();
    }
}

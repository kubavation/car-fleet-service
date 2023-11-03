package com.durys.jakub.carfleet.requests.transfer.instrastructure;

import com.durys.jakub.carfleet.requests.transfer.domain.TransferRequestRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class TransferRequestsConfiguration {

    @Bean
    TransferRequestRepository transferRequestRepository() {
        return new InMemoryTransferRequestRepository();
    }

}

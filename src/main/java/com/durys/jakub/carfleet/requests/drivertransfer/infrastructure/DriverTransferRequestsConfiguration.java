package com.durys.jakub.carfleet.requests.drivertransfer.infrastructure;

import com.durys.jakub.carfleet.requests.drivertransfer.domain.DriverTransferRequestRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class DriverTransferRequestsConfiguration {

    @Bean
    DriverTransferRequestRepository driverTransferRequestRepository() {
        return new MockedDriverTransferRequestRepository();
    }

}

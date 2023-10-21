package com.durys.jakub.carfleet.requests.prebooking.infrastructure;

import com.durys.jakub.carfleet.requests.prebooking.domain.PreBookingTransferRequestRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class PreBookingTransferRequestsConfiguration {

    @Bean
    PreBookingTransferRequestRepository preBookingTransferRequestRepository() {
        return new MockedPreBookingTransferRequestRepository();
    }

}

package com.durys.jakub.carfleet.drivers.infrastructure.external;

import com.durys.jakub.carfleet.drivers.domain.DriverId;
import com.durys.jakub.carfleet.drivers.domain.DriverRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
class UserAbsenceConfirmedEventHandler {

    private final DriverRepository driverRepository;


    @RabbitListener(queues = {"queue.users-absences"})
    public void handle(UserAbsenceConfirmed event) {

        log.info("handling {}", event);

        driverRepository.findById(new DriverId(event.userId()))
                .ifPresent(driver -> driver.markAsInactiveBetween(event.from(), event.to()));

    }

}

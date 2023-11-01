package com.durys.jakub.carfleet.drivers.infrastructure.external;

import com.durys.jakub.carfleet.drivers.application.DriversFleetUpdateService;
import com.durys.jakub.carfleet.drivers.infrastructure.external.provider.DriverProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
class DriversFleetChangedEventHandler {

    private final DriverProvider driverProvider;
    private final DriversFleetUpdateService driversFleetUpdateService;


    @RabbitListener(queues = {"${queue.users-with-role-change}"})
    public void handle(UsersWithRoleChanged event) {

        log.info("handling {}", event);

        driverProvider.loadAllBy(event.link())
                .collectList()
                .subscribe(driversFleetUpdateService::updateDriverFleet);
    }

}

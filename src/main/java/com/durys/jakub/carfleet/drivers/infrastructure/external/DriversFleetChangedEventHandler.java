package com.durys.jakub.carfleet.drivers.infrastructure.external;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
class DriversFleetChangedEventHandler {


    @RabbitListener(queues = {"${queue.users-with-role-change}"})
    public void handle(UsersWithRoleChanged event) {

        log.info("handling {}", event);
        
        //todo handle
    }

}

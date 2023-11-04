package com.durys.jakub.carfleet.requests.drivertransfer.infrastructure.in.event.handler;

import com.durys.jakub.carfleet.sharedkernel.requests.RequesterId;
import com.durys.jakub.carfleet.requests.drivertransfer.application.DriverTransferRequestService;
import com.durys.jakub.carfleet.requests.drivertransfer.infrastructure.in.event.DriverTransferRequestSubmitted;
import com.durys.jakub.carfleet.requests.vo.RequestPurpose;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
class DriverTransferRequestSubmittedEventHandler {

    private final DriverTransferRequestService driverTransferRequestService;

    public DriverTransferRequestSubmittedEventHandler(DriverTransferRequestService driverTransferRequestService) {
        this.driverTransferRequestService = driverTransferRequestService;
    }

    @RabbitListener(queues = {"${queue.driver-request-submission}"})
    public void handle(DriverTransferRequestSubmitted event) {
        log.info("handling {}", event);
        driverTransferRequestService.create(new RequesterId(event.requesterId()), event.from(), event.to(),
                new RequestPurpose(event.purpose()), event.departure(), event.purpose());
    }

}

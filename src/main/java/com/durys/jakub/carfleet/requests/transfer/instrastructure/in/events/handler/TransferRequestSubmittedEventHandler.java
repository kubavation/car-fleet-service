package com.durys.jakub.carfleet.requests.transfer.instrastructure.in.events.handler;

import com.durys.jakub.carfleet.requests.transfer.application.TransferRequestService;
import com.durys.jakub.carfleet.requests.transfer.domain.command.SubmitTransferRequestCommand;
import com.durys.jakub.carfleet.requests.transfer.instrastructure.in.events.TransferRequestEntered;
import com.durys.jakub.carfleet.sharedkernel.requests.RequesterId;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

@RequiredArgsConstructor
class TransferRequestSubmittedEventHandler {

    private final TransferRequestService transferRequestService;

    @RabbitListener(queues = {"${queue.transfer-request-submission}"})
    public void handle(TransferRequestEntered event) {
        transferRequestService.handle(
                new SubmitTransferRequestCommand(
                        new RequesterId(event.requesterId()), event.from(), event.to(), event.purpose(),
                        event.departure(), event.destination(), event.carType()));
    }
}

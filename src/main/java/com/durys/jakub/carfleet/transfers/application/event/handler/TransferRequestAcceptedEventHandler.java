package com.durys.jakub.carfleet.transfers.application.event.handler;

import com.durys.jakub.carfleet.requests.transfer.domain.event.TransferRequestAccepted;
import com.durys.jakub.carfleet.transfers.domain.Transfer;
import com.durys.jakub.carfleet.transfers.domain.TransferFactory;
import com.durys.jakub.carfleet.transfers.domain.TransferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class TransferRequestAcceptedEventHandler {

    private final TransferRepository transferRepository;

    @RabbitListener(queues = "todo")
    void handle(TransferRequestAccepted request) {

        Transfer transfer = TransferFactory.singleTransfer(request.requesterId(), request.requestId(),
                request.departure(), request.destination(), request.from(), request.to(), request.carId());

        transferRepository.save(transfer);
    }
}

package com.durys.jakub.carfleet.requests.transfer.instrastructure.in.events.handler;

import com.durys.jakub.carfleet.requests.transfer.application.TransferRequestService;
import com.durys.jakub.carfleet.requests.transfer.instrastructure.in.events.TransferRequestSubmitted;
import com.durys.jakub.carfleet.requests.vo.RequestPurpose;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;

@RequiredArgsConstructor
class TransferRequestSubmittedEventHandler {

    private final TransferRequestService transferRequestService;

    @EventListener
    public void handle(TransferRequestSubmitted event) {
        transferRequestService.create(event.requesterId(), event.from(), event.to(), new RequestPurpose(event.purpose()),
                event.placeFrom(), event.destination());
    }
}

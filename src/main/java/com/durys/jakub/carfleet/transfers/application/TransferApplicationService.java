package com.durys.jakub.carfleet.transfers.application;

import com.durys.jakub.carfleet.cars.domain.CarId;
import com.durys.jakub.carfleet.requests.transfer.domain.TransferRequestAssembler;
import com.durys.jakub.carfleet.sharedkernel.requests.RequestId;
import com.durys.jakub.carfleet.sharedkernel.requests.RequesterId;
import com.durys.jakub.carfleet.transfers.domain.Transfer;
import com.durys.jakub.carfleet.transfers.domain.TransferAssembler;
import com.durys.jakub.carfleet.transfers.domain.TransferFactory;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class TransferApplicationService {

    private final TransferAssembler transferAssembler;

    @Transactional
    public void create(RequesterId requesterId, RequestId requestId, String place,
                       String destination, LocalDateTime from, LocalDateTime to, CarId carId) {
        Transfer transfer = TransferFactory.singleTransfer(requesterId, requestId, place, destination, from, to, carId);

    }
}

package com.durys.jakub.carfleet.requests.drivertransfer.application;

import com.durys.jakub.carfleet.requests.drivertransfer.domain.DriverTransferRequest;
import com.durys.jakub.carfleet.requests.drivertransfer.domain.DriverTransferRequestAssembler;
import com.durys.jakub.carfleet.requests.RequestId;
import com.durys.jakub.carfleet.requests.RequesterId;
import com.durys.jakub.carfleet.requests.state.ChangeCommand;
import com.durys.jakub.carfleet.requests.state.State;
import com.durys.jakub.carfleet.requests.state.StateConfig;
import com.durys.jakub.carfleet.requests.vo.RequestPurpose;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DriverTransferRequestService {

    private final DriverTransferRequestAssembler assembler;

    public DriverTransferRequest create(RequesterId requesterId, LocalDateTime from, LocalDateTime to, RequestPurpose purpose) {

        DriverTransferRequest driverTransferRequest = new DriverTransferRequest(new RequestId(UUID.randomUUID()), requesterId, from, to, purpose);

        StateConfig<DriverTransferRequest> assemble = assembler.assemble();
        State<DriverTransferRequest> result = assemble.begin(driverTransferRequest);
        return driverTransferRequest; //todo save
    }


    public DriverTransferRequest change(RequestId requestId, LocalDateTime from, LocalDateTime to, RequestPurpose purpose) {

        //todo find request
        DriverTransferRequest driverTransferRequest = new DriverTransferRequest(requestId, new RequesterId(UUID.randomUUID()), from, to, purpose, "NEW");

        StateConfig<DriverTransferRequest> config = assembler.assemble();

        State<DriverTransferRequest> state = config.recreate(driverTransferRequest);
        State<DriverTransferRequest> changed = state.changeContent(
                new DriverTransferRequest(driverTransferRequest.getRequestId(), driverTransferRequest.getRequesterId(), from, to, purpose));

        return changed.getObject();
    }


    public DriverTransferRequest changeStatus(RequestId requestId, ChangeCommand command) {

        DriverTransferRequest driverTransferRequest = new DriverTransferRequest(requestId, new RequesterId(UUID.randomUUID()),
                LocalDateTime.now(), LocalDateTime.now() , new RequestPurpose("content"), "NEW"); //todo

        StateConfig<DriverTransferRequest> config = assembler.assemble();

        State<DriverTransferRequest> state = config.recreate(driverTransferRequest);

        //todo
        State<DriverTransferRequest> changed = state.changeState(command);
        return changed.getObject();
    }
}

package com.durys.jakub.carfleet.requests.application;

import com.durys.jakub.carfleet.drivers.DriverId;
import com.durys.jakub.carfleet.requests.Request;
import com.durys.jakub.carfleet.requests.RequestAssembler;
import com.durys.jakub.carfleet.requests.RequestId;
import com.durys.jakub.carfleet.requests.RequesterId;
import com.durys.jakub.carfleet.requests.state.ChangeCommand;
import com.durys.jakub.carfleet.requests.state.State;
import com.durys.jakub.carfleet.requests.state.StateConfig;
import com.durys.jakub.carfleet.requests.vo.RequestContent;
import com.durys.jakub.carfleet.requests.vo.RequestPurpose;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class RequestService {

    private final RequestAssembler assembler;

    public Request create(RequesterId requesterId,
                       LocalDateTime from, LocalDateTime to, RequestPurpose purpose) {

        Request request = new Request(new RequestId(UUID.randomUUID()), requesterId, from, to, purpose);

        StateConfig<Request> assemble = assembler.assemble();
        State<Request> result = assemble.begin(request);
        return request; //todo save
    }


    public Request change(RequestId requestId, LocalDateTime from, LocalDateTime to, RequestPurpose purpose) {

        //todo find request
        Request request = new Request(requestId, new RequesterId(UUID.randomUUID()), from, to, purpose, "NEW");

        StateConfig<Request> config = assembler.assemble();

        State<Request> state = config.recreate(request);
        State<Request> changed = state.changeContent(new Request(request.getRequestId(), request.getRequesterId(), from, to, purpose));

        return (Request) changed.getObject();
    }

    public Request changeStatus(RequestId requestId, DriverId driverId) {

        Request request = new Request(requestId, new RequesterId(UUID.randomUUID()),
                LocalDateTime.now(), LocalDateTime.now() , new RequestPurpose("content"), "NEW"); //todo

        StateConfig<Request> config = assembler.assemble();

        State<Request> state = config.recreate(request);

        //todo
        State<Request> changed = state.changeState(new ChangeCommand("ACCEPTED", Map.of("driverId", driverId)));
        return changed.getObject();
    }
}

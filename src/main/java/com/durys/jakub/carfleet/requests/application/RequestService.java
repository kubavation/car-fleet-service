package com.durys.jakub.carfleet.requests.application;

import com.durys.jakub.carfleet.requests.Request;
import com.durys.jakub.carfleet.requests.RequestAssembler;
import com.durys.jakub.carfleet.requests.RequestId;
import com.durys.jakub.carfleet.requests.RequesterId;
import com.durys.jakub.carfleet.requests.state.State;
import com.durys.jakub.carfleet.requests.state.StateConfig;
import com.durys.jakub.carfleet.requests.vo.RequestPurpose;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class RequestService {

    private final RequestAssembler assembler;

    public Request create(RequesterId requesterId,
                       LocalDateTime from, LocalDateTime to, RequestPurpose purpose) {

        Request request = new Request(new RequestId(UUID.randomUUID()), requesterId, from, to, purpose);

        StateConfig assemble = assembler.assemble();
        State result = assemble.begin(request);
        return request; //todo save
    }

}

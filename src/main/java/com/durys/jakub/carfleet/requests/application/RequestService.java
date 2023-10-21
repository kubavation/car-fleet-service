package com.durys.jakub.carfleet.requests.application;

import com.durys.jakub.carfleet.requests.Request;
import com.durys.jakub.carfleet.requests.RequestAssembler;
import com.durys.jakub.carfleet.requests.RequestId;
import com.durys.jakub.carfleet.requests.RequesterId;
import com.durys.jakub.carfleet.requests.state.State;
import com.durys.jakub.carfleet.requests.state.StateConfig;
import com.durys.jakub.carfleet.requests.vo.RequestContent;
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

    public Request change(RequestId requestId, LocalDateTime from, LocalDateTime to, RequestPurpose purpose) {

        //todo find request
        Request request = new Request(requestId, new RequesterId(UUID.randomUUID()), from, to, purpose, "NEW");

        StateConfig config = assembler.assemble();

        State state = config.recreate(request);
        State changed = state.changeContent(new RequestContent(from, to, purpose));

        return changed.getRequest();
    }
}

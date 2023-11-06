package com.durys.jakub.carfleet.requests.transfer.instrastructure.in;

import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;

import java.util.*;
import java.util.stream.Collectors;

@Getter
class RestResponse extends RepresentationModel<RestResponse> {

    enum OperationStatus {
       Success, Failure
    }

    private final UUID resourceId;
    private final OperationStatus status;
    private final Set<String> additionalMessages;

    private RestResponse(UUID resourceId, OperationStatus status, List<Exception> exceptions) {
        this.resourceId = resourceId;
        this.status = status;
        this.additionalMessages = exceptions.stream()
                .map(Throwable::getMessage)
                .collect(Collectors.toSet());
    }

    static RestResponse success(UUID resourceId) {
        return new RestResponse(resourceId, OperationStatus.Success, Collections.emptyList());
    }

    static RestResponse failure(UUID resourceId, List<Exception> exceptions) {
        return new RestResponse(resourceId, OperationStatus.Failure, exceptions);
    }
}

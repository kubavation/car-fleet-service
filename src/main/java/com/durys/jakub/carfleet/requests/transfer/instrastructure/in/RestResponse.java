package com.durys.jakub.carfleet.requests.transfer.instrastructure.in;

import com.durys.jakub.carfleet.common.errors.ValidationError;
import com.durys.jakub.carfleet.common.errors.ValidationErrors;
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

    private RestResponse(UUID resourceId, OperationStatus status, ValidationErrors validationErrors) {
        this.resourceId = resourceId;
        this.status = status;
        this.additionalMessages = validationErrors.errors().stream()
                .map(Throwable::getMessage)
                .collect(Collectors.toSet());
    }

    private RestResponse(UUID resourceId, OperationStatus status, Set<String> errorMessages) {
        this.resourceId = resourceId;
        this.status = status;
        this.additionalMessages = errorMessages;
    }

    static RestResponse success(UUID resourceId) {
        return new RestResponse(resourceId, OperationStatus.Success, new ValidationErrors(Collections.emptyList()));
    }

    static RestResponse failure(UUID resourceId, ValidationErrors errors) {
        return new RestResponse(resourceId, OperationStatus.Failure, errors);
    }

    static RestResponse failure(UUID resourceId, Set<String> errorMessages) {
        return new RestResponse(resourceId, OperationStatus.Failure, errorMessages);
    }
}

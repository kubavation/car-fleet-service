package com.durys.jakub.carfleet.requests.transfer.instrastructure.in;

import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.util.UUID;

@RequiredArgsConstructor
class RestResponse extends RepresentationModel<RestResponse> {
    private final UUID resourceId;
}

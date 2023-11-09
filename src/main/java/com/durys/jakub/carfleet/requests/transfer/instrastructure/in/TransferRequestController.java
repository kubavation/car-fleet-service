package com.durys.jakub.carfleet.requests.transfer.instrastructure.in;

import com.durys.jakub.carfleet.cars.domain.CarId;
import com.durys.jakub.carfleet.common.errors.ValidationErrors;
import com.durys.jakub.carfleet.requests.transfer.application.TransferRequestService;
import com.durys.jakub.carfleet.requests.transfer.domain.TransferRequest;
import com.durys.jakub.carfleet.requests.transfer.domain.command.ChangeTransferRequestContentCommand;
import com.durys.jakub.carfleet.requests.transfer.domain.command.SubmitTransferRequestCommand;
import com.durys.jakub.carfleet.requests.transfer.domain.state.commands.AssignTransferCarCommand;
import com.durys.jakub.carfleet.requests.transfer.instrastructure.in.model.SubmitTransferRequest;
import com.durys.jakub.carfleet.sharedkernel.requests.RequestId;
import com.durys.jakub.carfleet.sharedkernel.requests.RequesterId;
import com.durys.jakub.carfleet.state.ChangeCommand;
import io.vavr.control.Either;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.durys.jakub.carfleet.requests.transfer.domain.TransferRequest.Status.CANCELLED;
import static com.durys.jakub.carfleet.requests.transfer.domain.TransferRequest.Status.REJECTED;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/transfer-requests")
class TransferRequestController {

    private final TransferRequestService transferRequestService;

    TransferRequestController(TransferRequestService transferRequestService) {
        this.transferRequestService = transferRequestService;
    }

    @PostMapping
    ResponseEntity<RestResponse> submit(@RequestBody SubmitTransferRequest transferRequest) {

        var response = transferRequestService.handle(
                new SubmitTransferRequestCommand(
                        new RequesterId(transferRequest.requesterId()),
                        transferRequest.from(), transferRequest.to(),
                        transferRequest.purpose(), transferRequest.departure(),
                        transferRequest.destination(), transferRequest.carType()));

        if (response.isLeft()) {
            return ResponseEntity.ok(toResponse(null, response));
        }

        return response
                .map(result ->
                        ResponseEntity.status(HttpStatus.CREATED)
                            .body(RestResponse.success(null).add(resourceLinks(transferRequest, result)))).get();
    }

    @PatchMapping("/{requestId}")
    ResponseEntity<RestResponse> changeContent(@PathVariable UUID requestId, @RequestBody SubmitTransferRequest transferRequest) {

        var response = transferRequestService.change(
                new ChangeTransferRequestContentCommand(
                        new RequestId(requestId), transferRequest.from(), transferRequest.to(), transferRequest.purpose(),
                        transferRequest.departure(), transferRequest.destination(), transferRequest.carType()));

        return response
                .map(result ->
                        ResponseEntity.status(HttpStatus.OK)
                                .body(RestResponse.success(null).add(resourceLinks(transferRequest, result)))).get();
    }

    @PatchMapping("/{requestId}/rejection")
    ResponseEntity<EntityModel<RestResponse>> reject(@PathVariable UUID requestId) {

        var result = transferRequestService.changeStatus(new RequestId(requestId), new ChangeCommand(REJECTED));

        return ResponseEntity.ok()
                .body(EntityModel
                        .of(toResponse(requestId, result)));
    }

    @PatchMapping("/{requestId}/cancellation")
    ResponseEntity<EntityModel<RestResponse>> cancel(@PathVariable UUID requestId) {

        var result =  transferRequestService.changeStatus(new RequestId(requestId), new ChangeCommand(CANCELLED));

        return ResponseEntity.ok()
                .body(EntityModel
                        .of(toResponse(requestId, result)));
    }

    @PatchMapping("/{requestId}/assigned-car")
    ResponseEntity<EntityModel<RestResponse>> assignCar(@PathVariable UUID requestId, @RequestParam UUID assignedCarId) {

        var result = transferRequestService.changeStatus(new RequestId(requestId),
                new AssignTransferCarCommand(new CarId(assignedCarId)));

        return ResponseEntity.ok()
                .body(EntityModel
                        .of(toResponse(requestId, result)));
    }

    @PatchMapping("/{requestId}/acceptation")
    ResponseEntity<EntityModel<RestResponse>> accept(@PathVariable UUID requestId) {

        var result = transferRequestService.changeStatus(new RequestId(requestId), new ChangeCommand(TransferRequest.Status.ACCEPTED));

        return ResponseEntity.ok()
                .body(EntityModel
                        .of(toResponse(requestId, result)));
    }


    private static List<Link> resourceLinks(SubmitTransferRequest request, TransferRequest model) {
        return List.of(
                linkTo(methodOn(TransferRequestController.class)
                        .changeContent(model.requestId().value(), request)).withRel("change-content"),
                linkTo(methodOn(TransferRequestController.class)
                        .reject(model.requestId().value())).withRel("reject"),
                linkTo(methodOn(TransferRequestController.class)
                        .cancel(model.requestId().value())).withRel("cancel"),
                linkTo(methodOn(TransferRequestController.class)
                        .accept(model.requestId().value())).withRel("accept"),
                linkTo(methodOn(TransferRequestController.class)
                        .assignCar(model.requestId().value(), UUID.randomUUID())).withRel("assign-car"));
    }

    private static RestResponse toResponse(UUID resourceId, Either<ValidationErrors, TransferRequest> result) {
        return result
                .fold(
                    exceptions -> RestResponse.failure(resourceId, exceptions),
                    request -> RestResponse.success(resourceId));
    }



}

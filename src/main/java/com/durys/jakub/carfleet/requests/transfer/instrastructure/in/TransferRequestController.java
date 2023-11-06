package com.durys.jakub.carfleet.requests.transfer.instrastructure.in;

import com.durys.jakub.carfleet.cars.domain.CarId;
import com.durys.jakub.carfleet.requests.transfer.application.TransferRequestService;
import com.durys.jakub.carfleet.requests.transfer.domain.TransferRequest;
import com.durys.jakub.carfleet.requests.transfer.domain.state.commands.AssignTransferCarCommand;
import com.durys.jakub.carfleet.requests.transfer.instrastructure.in.model.SubmitTransferRequest;
import com.durys.jakub.carfleet.sharedkernel.requests.RequestId;
import com.durys.jakub.carfleet.sharedkernel.requests.RequesterId;
import com.durys.jakub.carfleet.state.ChangeCommand;
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
    ResponseEntity<EntityModel<RestResponse>> submit(@RequestBody SubmitTransferRequest transferRequest) {

        TransferRequest created = transferRequestService.create(
                new RequesterId(transferRequest.requesterId()),
                transferRequest.from(), transferRequest.to(),
                transferRequest.purpose(), transferRequest.departure(),
                transferRequest.destination(), transferRequest.carType());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                    EntityModel.of(new RestResponse(created.requestId().value()),
                        List.of(
                            linkTo(methodOn(TransferRequestController.class)
                                    .changeContent(created.requestId().value(), transferRequest)).withRel("change-content"))
                    ));
    }

    @PatchMapping("/{requestId}")
    ResponseEntity<EntityModel<Void>> changeContent(@PathVariable UUID requestId, @RequestBody SubmitTransferRequest transferRequest) {

        transferRequestService.change(new RequestId(requestId), transferRequest.from(), transferRequest.to(),
                transferRequest.purpose(), transferRequest.departure(),
                transferRequest.destination(), transferRequest.carType());

        return ResponseEntity.ok().body(null);
    }

    @PatchMapping("/{requestId}/rejection")
    void reject(@PathVariable UUID requestId) {
        transferRequestService.changeStatus(new RequestId(requestId), new ChangeCommand(REJECTED));
    }

    @PatchMapping("/{requestId}/cancellation")
    void cancel(@PathVariable UUID requestId) {
        transferRequestService.changeStatus(new RequestId(requestId), new ChangeCommand(CANCELLED));
    }

    @PatchMapping("/{requestId}/acceptation")
    void accept(@PathVariable UUID requestId, @RequestParam UUID assignedCarId) {
        transferRequestService.changeStatus(new RequestId(requestId), new AssignTransferCarCommand(new CarId(assignedCarId)));
    }

}

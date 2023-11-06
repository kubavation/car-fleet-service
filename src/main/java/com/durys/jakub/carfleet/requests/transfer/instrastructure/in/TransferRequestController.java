package com.durys.jakub.carfleet.requests.transfer.instrastructure.in;

import com.durys.jakub.carfleet.cars.domain.CarId;
import com.durys.jakub.carfleet.requests.transfer.application.TransferRequestService;
import com.durys.jakub.carfleet.requests.transfer.domain.state.commands.AssignTransferCarCommand;
import com.durys.jakub.carfleet.requests.transfer.instrastructure.in.model.SubmitTransferRequest;
import com.durys.jakub.carfleet.sharedkernel.requests.RequestId;
import com.durys.jakub.carfleet.sharedkernel.requests.RequesterId;
import com.durys.jakub.carfleet.state.ChangeCommand;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static com.durys.jakub.carfleet.requests.transfer.domain.TransferRequest.Status.CANCELLED;
import static com.durys.jakub.carfleet.requests.transfer.domain.TransferRequest.Status.REJECTED;

@RestController
@RequestMapping("/transfer-requests")
class TransferRequestController {

    private final TransferRequestService transferRequestService;

    TransferRequestController(TransferRequestService transferRequestService) {
        this.transferRequestService = transferRequestService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    void submit(@RequestBody SubmitTransferRequest transferRequest) {
        transferRequestService.create(
                new RequesterId(transferRequest.requesterId()),
                transferRequest.from(), transferRequest.to(),
                transferRequest.purpose(), transferRequest.departure(),
                transferRequest.destination(), transferRequest.carType());
    }

    @PatchMapping("/{requestId}")
    void changeContent(@PathVariable UUID requestId, @RequestBody SubmitTransferRequest transferRequest) {

        transferRequestService.change(new RequestId(requestId), transferRequest.from(), transferRequest.to(),
                transferRequest.purpose(), transferRequest.departure(),
                transferRequest.destination(), transferRequest.carType());
    }

    @PatchMapping("/{requestId}/rejection")
    void reject(@PathVariable UUID requestId) {
        transferRequestService.changeStatus(new RequestId(requestId), new ChangeCommand(REJECTED));
    }

    @PatchMapping("/{requestId}/cancelation")
    void cancel(@PathVariable UUID requestId) {
        transferRequestService.changeStatus(new RequestId(requestId), new ChangeCommand(CANCELLED));
    }

    @PatchMapping("/{requestId}/acceptation")
    void accept(@PathVariable UUID requestId, @RequestParam UUID assignedCarId) {
        transferRequestService.changeStatus(new RequestId(requestId), new AssignTransferCarCommand(new CarId(assignedCarId)));
    }

}

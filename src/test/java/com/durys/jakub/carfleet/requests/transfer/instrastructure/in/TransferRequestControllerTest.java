package com.durys.jakub.carfleet.requests.transfer.instrastructure.in;

import com.durys.jakub.carfleet.cars.domain.CarId;
import com.durys.jakub.carfleet.common.errors.ValidationError;
import com.durys.jakub.carfleet.common.errors.ValidationErrors;
import com.durys.jakub.carfleet.requests.transfer.application.TransferRequestService;
import com.durys.jakub.carfleet.requests.transfer.domain.TransferRequest;
import com.durys.jakub.carfleet.requests.transfer.domain.command.ChangeTransferRequestContentCommand;
import com.durys.jakub.carfleet.requests.transfer.domain.command.SubmitTransferRequestCommand;
import com.durys.jakub.carfleet.requests.transfer.domain.state.commands.AssignTransferCarCommand;
import com.durys.jakub.carfleet.requests.transfer.instrastructure.in.model.SubmitTransferRequest;
import com.durys.jakub.carfleet.sharedkernel.cars.CarType;
import com.durys.jakub.carfleet.sharedkernel.requests.RequestId;
import com.durys.jakub.carfleet.sharedkernel.requests.RequesterId;
import com.durys.jakub.carfleet.state.ChangeCommand;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.vavr.control.Either;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static com.durys.jakub.carfleet.requests.transfer.domain.TransferRequest.Status.CANCELLED;
import static com.durys.jakub.carfleet.requests.transfer.domain.TransferRequest.Status.REJECTED;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TransferRequestController.class)
class TransferRequestControllerTest {

    @MockBean
    private TransferRequestService transferRequestService;

    @InjectMocks
    private TransferRequestController transferRequestController;

    @Autowired
    private MockMvc mockMvc;


    private static final ObjectMapper objectMapper = init();

    @Test
    void createTransferRequest_shouldReturn201() throws Exception {

        var request = new SubmitTransferRequest(UUID.randomUUID(),
                LocalDateTime.now(), LocalDateTime.now().plusDays(1),
                "Departure", "Destination", "Purpose",
                CarType.Passenger);

        var result = new TransferRequest(new RequestId(UUID.randomUUID()),
                new RequesterId(request.requesterId()),
                request.from(), request.to(), request.purpose(), request.departure(),
                request.destination(), request.carType());

        Mockito.when(transferRequestService
                .handle(new SubmitTransferRequestCommand(
                            new RequesterId(request.requesterId()), request.from(), request.to(),
                            request.purpose(), request.departure(), request.destination(), request.carType())))
                .thenReturn(Either.right(result));

        mockMvc.perform(post("/transfer-requests")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$._links.change-content.href")
                        .value(containsString("/transfer-requests/%s".formatted(result.requestId().value()))))
                .andExpect(jsonPath("$._links.reject.href")
                        .value(containsString("/transfer-requests/%s/rejection".formatted(result.requestId().value()))))
                .andExpect(jsonPath("$._links.cancel.href")
                        .value(containsString("/transfer-requests/%s/cancellation".formatted(result.requestId().value()))))
                .andExpect(jsonPath("$._links.accept.href")
                        .value(containsString("/transfer-requests/%s/acceptation".formatted(result.requestId().value()))))
                .andReturn();
    }

    @Test
    void editTransferRequest_shouldReturn200() throws Exception {

        var request = new SubmitTransferRequest(UUID.randomUUID(),
                LocalDateTime.now(), LocalDateTime.now().plusDays(1),
                "Departure", "Destination", "Purpose",
                CarType.Passenger);

        UUID requestId = UUID.randomUUID();

        var result = new TransferRequest(new RequestId(requestId),
                new RequesterId(request.requesterId()),
                request.from(), request.to(), request.purpose(), request.departure(), request.destination(), request.carType());


        Mockito.when(transferRequestService
                        .change(new ChangeTransferRequestContentCommand(
                                    new RequestId(requestId), request.from(), request.to(), request.purpose(),
                                    request.departure(), request.destination(), request.carType())))
                .thenReturn(Either.right(result));

       mockMvc.perform(MockMvcRequestBuilders
                        .patch("/transfer-requests/%s".formatted(requestId.toString()))
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._links.change-content.href")
                        .value(containsString("/transfer-requests/%s".formatted(result.requestId().value()))))
                .andExpect(jsonPath("$._links.reject.href")
                        .value(containsString("/transfer-requests/%s/rejection".formatted(result.requestId().value()))))
                .andExpect(jsonPath("$._links.cancel.href")
                        .value(containsString("/transfer-requests/%s/cancellation".formatted(result.requestId().value()))))
                .andExpect(jsonPath("$._links.accept.href")
                        .value(containsString("/transfer-requests/%s/acceptation".formatted(result.requestId().value()))))
                .andReturn();
    }

    @Test
    void rejectTransferRequest_shouldReturn200() throws Exception {

        UUID requestId = UUID.randomUUID();

        var result = new TransferRequest(new RequestId(requestId),
                new RequesterId(UUID.randomUUID()),
                LocalDateTime.now(), LocalDateTime.now().plusDays(1), "Purpose",
                "Departure", "Destination", CarType.Passenger);

        Mockito.when(transferRequestService.changeStatus(new RequestId(requestId), new ChangeCommand(REJECTED)))
                .thenReturn(Either.right(result));

        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders
                        .patch("/transfer-requests/%s/rejection".formatted(requestId.toString()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    void rejectTransferRequest_shouldReturn200AndErrorDetails() throws Exception {

        UUID requestId = UUID.randomUUID();

        Mockito.when(transferRequestService.changeStatus(new RequestId(requestId), new ChangeCommand(REJECTED)))
                .thenReturn(
                        Either.left(
                                new ValidationErrors(
                                        List.of(new ValidationError("Unexpected Exception")))
                        ));

        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/transfer-requests/%s/rejection".formatted(requestId.toString()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("Failure"))
                .andExpect(jsonPath("$.additionalMessages[0]").value("Unexpected Exception"));
    }

    @Test
    void cancelTransferRequest_shouldReturn200() throws Exception {

        UUID requestId = UUID.randomUUID();

        var result = new TransferRequest(new RequestId(requestId),
                new RequesterId(UUID.randomUUID()),
                LocalDateTime.now(), LocalDateTime.now().plusDays(1), "Purpose",
                "Departure", "Destination", CarType.Passenger);

        Mockito.when(transferRequestService.changeStatus(new RequestId(requestId), new ChangeCommand(CANCELLED)))
                .thenReturn(Either.right(result));

        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/transfer-requests/%s/cancellation".formatted(requestId.toString()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void cancelTransferRequest_shouldReturn200AndErrorDetails() throws Exception {


        UUID requestId = UUID.randomUUID();

        Mockito.when(transferRequestService.changeStatus(new RequestId(requestId), new ChangeCommand(CANCELLED)))
                .thenReturn(Either.left(
                        new ValidationErrors(
                            List.of(
                                new ValidationError("Unexpected Exception 1"),
                                new ValidationError("Unexpected Exception 2")))
                ));

        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/transfer-requests/%s/cancellation".formatted(requestId.toString()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("Failure"))
                .andExpect(jsonPath("$.additionalMessages")
                        .value(containsInAnyOrder("Unexpected Exception 1", "Unexpected Exception 2")));
    }

    @Test
    void acceptTransferRequest_shouldReturn200() throws Exception {

        UUID requestId = UUID.randomUUID();
        UUID carId = UUID.randomUUID();

        var result = new TransferRequest(new RequestId(requestId),
                new RequesterId(UUID.randomUUID()),
                LocalDateTime.now(), LocalDateTime.now().plusDays(1), "Purpose",
                "Departure", "Destination", CarType.Passenger);

        Mockito.when(transferRequestService.changeStatus(new RequestId(requestId), new ChangeCommand(TransferRequest.Status.ACCEPTED)))
                .thenReturn(Either.right(result));

        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/transfer-requests/%s/acceptation".formatted(requestId.toString()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    void assignCarToTransferRequest_shouldReturn200() throws Exception {

        UUID requestId = UUID.randomUUID();
        UUID carId = UUID.randomUUID();

        var result = new TransferRequest(new RequestId(requestId),
                new RequesterId(UUID.randomUUID()),
                LocalDateTime.now(), LocalDateTime.now().plusDays(1), "Purpose",
                "Departure", "Destination", CarType.Passenger);

        Mockito.when(transferRequestService.changeStatus(new RequestId(requestId), new AssignTransferCarCommand(new CarId(carId))))
                .thenReturn(Either.right(result));

        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/transfer-requests/%s/assigned-car".formatted(requestId.toString()))
                        .param("assignedCarId", carId.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    void assignCarToTransferRequest_shouldReturn200AndErrorDetails() throws Exception {

        UUID requestId = UUID.randomUUID();
        UUID carId = UUID.randomUUID();

        Mockito.when(transferRequestService.changeStatus(new RequestId(requestId), new AssignTransferCarCommand(new CarId(carId))))
                .thenReturn(Either.left(
                        new ValidationErrors(
                                List.of(
                                        new ValidationError("Unexpected Exception 1"),
                                        new ValidationError("Unexpected Exception 2")))
                ));


        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/transfer-requests/%s/assigned-car".formatted(requestId.toString()))
                        .param("assignedCarId", carId.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("Failure"))
                .andExpect(jsonPath("$.additionalMessages")
                        .value(containsInAnyOrder("Unexpected Exception 1", "Unexpected Exception 2")));

    }



    @Test
    void acceptTransferRequest_shouldReturn200AndErrorDetails() throws Exception {

        UUID requestId = UUID.randomUUID();

        Mockito.when(transferRequestService.changeStatus(new RequestId(requestId), new ChangeCommand(TransferRequest.Status.ACCEPTED)))
                .thenReturn(Either.left(
                        new ValidationErrors(
                                List.of(
                                        new ValidationError("Unexpected Exception 1"),
                                        new ValidationError("Unexpected Exception 2")))
                ));


        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/transfer-requests/%s/acceptation".formatted(requestId.toString()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("Failure"))
                .andExpect(jsonPath("$.additionalMessages")
                        .value(containsInAnyOrder("Unexpected Exception 1", "Unexpected Exception 2")));

    }


    private static ObjectMapper init() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        return mapper;
    }

}
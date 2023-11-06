package com.durys.jakub.carfleet.requests.transfer.instrastructure.in;

import com.durys.jakub.carfleet.requests.transfer.application.TransferRequestService;
import com.durys.jakub.carfleet.requests.transfer.domain.TransferRequest;
import com.durys.jakub.carfleet.requests.transfer.instrastructure.in.model.SubmitTransferRequest;
import com.durys.jakub.carfleet.sharedkernel.cars.CarType;
import com.durys.jakub.carfleet.sharedkernel.requests.RequestId;
import com.durys.jakub.carfleet.sharedkernel.requests.RequesterId;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.UUID;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
                LocalDateTime.now(), LocalDateTime.now(),
                "Departure", "Destination", "Purpose",
                CarType.Passenger);

        var result = new TransferRequest(new RequestId(UUID.randomUUID()),
                new RequesterId(request.requesterId()),
                request.from(), request.to(), request.purpose(), request.departure(), request.destination(), request.carType());

        Mockito.when(transferRequestService
                .create(new RequesterId(request.requesterId()), request.from(), request.to(),
                        request.purpose(), request.departure(), request.destination(), request.carType()))
                .thenReturn(result);

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
                LocalDateTime.now(), LocalDateTime.now(),
                "Departure", "Destination", "Purpose",
                CarType.Passenger);

        UUID requestId = UUID.randomUUID();

        var result = new TransferRequest(new RequestId(requestId),
                new RequesterId(request.requesterId()),
                request.from(), request.to(), request.purpose(), request.departure(), request.destination(), request.carType());


        Mockito.when(transferRequestService
                        .change(new RequestId(requestId), request.from(), request.to(),
                                request.purpose(), request.departure(), request.destination(), request.carType()))
                .thenReturn(result);

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

        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders
                        .patch("/transfer-requests/%s/rejection".formatted(requestId.toString()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    void cancelTransferRequest_shouldReturn200() throws Exception {


        UUID requestId = UUID.randomUUID();

        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders
                        .patch("/transfer-requests/%s/cancellation".formatted(requestId.toString()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    void acceptTransferRequest_shouldReturn200() throws Exception {

        UUID requestId = UUID.randomUUID();
        UUID carId = UUID.randomUUID();

        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders
                        .patch("/transfer-requests/%s/acceptation".formatted(requestId.toString()))
                        .param("assignedCarId", carId.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }


    private static ObjectMapper init() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        return mapper;
    }

}
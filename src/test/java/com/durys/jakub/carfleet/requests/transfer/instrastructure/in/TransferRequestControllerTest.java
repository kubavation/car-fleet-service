package com.durys.jakub.carfleet.requests.transfer.instrastructure.in;

import com.durys.jakub.carfleet.requests.transfer.application.TransferRequestService;
import com.durys.jakub.carfleet.requests.transfer.instrastructure.in.model.SubmitTransferRequest;
import com.durys.jakub.carfleet.sharedkernel.cars.CarType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

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

        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders
                        .post("/transfer-requests")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
    }

    @Test
    void editTransferRequest_shouldReturn200() throws Exception {

        var request = new SubmitTransferRequest(UUID.randomUUID(),
                LocalDateTime.now(), LocalDateTime.now(),
                "Departure", "Destination", "Purpose",
                CarType.Passenger);

        UUID requestId = UUID.randomUUID();

        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders
                        .patch("/transfer-requests/%s".formatted(requestId.toString()))
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
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
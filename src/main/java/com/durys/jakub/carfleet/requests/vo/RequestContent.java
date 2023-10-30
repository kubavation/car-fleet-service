package com.durys.jakub.carfleet.requests.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
public class RequestContent {
    private LocalDateTime from;
    private LocalDateTime to;
    private RequestPurpose purpose;
    private String departure;
    private String destination;
}

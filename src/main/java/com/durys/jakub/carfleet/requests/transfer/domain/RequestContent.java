package com.durys.jakub.carfleet.requests.transfer.domain;

import com.durys.jakub.carfleet.requests.vo.RequestPurpose;

import java.time.LocalDateTime;

record RequestContent(LocalDateTime from, LocalDateTime to, RequestPurpose purpose, String departure, String destination) {
}

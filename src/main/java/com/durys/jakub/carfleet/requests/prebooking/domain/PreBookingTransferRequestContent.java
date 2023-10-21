package com.durys.jakub.carfleet.requests.prebooking.domain;

import com.durys.jakub.carfleet.cars.domain.CarId;
import com.durys.jakub.carfleet.drivers.DriverId;
import com.durys.jakub.carfleet.requests.vo.RequestPurpose;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
public class PreBookingTransferRequestContent {
    private LocalDateTime from;
    private LocalDateTime to;
    private RequestPurpose purpose;
    private CarId carId;
    private DriverId driverId;
}

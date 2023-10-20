package com.durys.jakub.carfleet.drivers;

import com.durys.jakub.carfleet.common.Status;

public class Driver {

    private final DriverId driverId;
    private final DriverInformation driverInformation;
    private final Status status;


    public Driver(DriverId driverId, DriverInformation driverInformation, Status status) {
        this.driverId = driverId;
        this.driverInformation = driverInformation;
        this.status = status;
    }

    public Driver(DriverId driverId, DriverInformation driverInformation) {
        this(driverId, driverInformation, Status.ACTIVE);
    }
}

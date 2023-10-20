package com.durys.jakub.carfleet.drivers;

import com.durys.jakub.carfleet.common.Status;

public class Driver {

    private final DriverId driverId;
    private final DriverPersonalInformation driverInformation;
    private final Status status;


    public Driver(DriverId driverId, DriverPersonalInformation driverPersonalInformation, Status status) {
        this.driverId = driverId;
        this.driverInformation = driverPersonalInformation;
        this.status = status;
    }

    public Driver(DriverId driverId, DriverPersonalInformation driverPersonalInformation) {
        this(driverId, driverPersonalInformation, Status.ACTIVE);
    }
}

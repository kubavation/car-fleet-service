package com.durys.jakub.carfleet.drivers.domain;

import com.durys.jakub.carfleet.common.Status;
import jakarta.persistence.Table;

@Table
public class Driver {

    private final DriverId driverId;
    private String firstName;
    private String lastName;
    private Status status;

    public Driver(DriverId driverId, String firstName, String lastName, Status status) {
        this.driverId = driverId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.status = status;
    }

    public Driver(DriverId driverId, String firstName, String lastName) {
        this(driverId, firstName, lastName, Status.ACTIVE);
    }

    public void archive() {
        this.status = Status.ARCHIVED;
    }

    public void activate() {
        this.status = Status.ACTIVE;
    }

    public void updatePersonalInformation(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

}

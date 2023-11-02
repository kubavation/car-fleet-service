package com.durys.jakub.carfleet.drivers.domain;

import java.io.Serializable;
import java.util.UUID;

public record DriverId(UUID value) implements Serializable {
}

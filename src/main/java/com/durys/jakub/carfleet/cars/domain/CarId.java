package com.durys.jakub.carfleet.cars.domain;

import java.io.Serializable;
import java.util.UUID;

public record CarId(UUID value) implements Serializable {
}

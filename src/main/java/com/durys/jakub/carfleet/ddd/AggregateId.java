package com.durys.jakub.carfleet.ddd;

import java.io.Serializable;
import java.util.UUID;

public record AggregateId(UUID value) implements Serializable {
}

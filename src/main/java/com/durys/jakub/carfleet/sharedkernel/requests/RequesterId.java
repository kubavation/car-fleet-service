package com.durys.jakub.carfleet.sharedkernel.requests;

import java.io.Serializable;
import java.util.UUID;

public record RequesterId(UUID value) implements Serializable {
}

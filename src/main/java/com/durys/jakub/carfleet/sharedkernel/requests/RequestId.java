package com.durys.jakub.carfleet.sharedkernel.requests;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public record RequestId(UUID value) implements Serializable {
}

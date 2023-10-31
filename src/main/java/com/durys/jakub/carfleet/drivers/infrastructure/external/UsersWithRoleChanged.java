package com.durys.jakub.carfleet.drivers.infrastructure.external;

import java.time.Instant;
import java.util.UUID;

public record UsersWithRoleChanged(UUID id, Instant at, String type, String role, String link) { }

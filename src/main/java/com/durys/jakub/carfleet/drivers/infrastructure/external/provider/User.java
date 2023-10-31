package com.durys.jakub.carfleet.drivers.infrastructure.external.provider;


import java.util.UUID;

record User(UserId userId, String firstName, String lastName) {
}


record UserId(UUID value) {}
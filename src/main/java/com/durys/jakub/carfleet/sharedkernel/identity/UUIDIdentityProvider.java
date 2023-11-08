package com.durys.jakub.carfleet.sharedkernel.identity;

import java.util.UUID;

public class UUIDIdentityProvider implements IdentityProvider<UUID> {

    @Override
    public UUID generate() {
        return UUID.randomUUID();
    }
}

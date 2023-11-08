package com.durys.jakub.carfleet.sharedkernel.identity;

public interface IdentityProvider<T> {
    T generate();
}

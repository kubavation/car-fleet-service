package com.durys.jakub.carfleet.state;

public interface Assembler<T extends Flowable<T>> {
    StateConfig<T> configuration();
    StateConfig<T> assemble();
}

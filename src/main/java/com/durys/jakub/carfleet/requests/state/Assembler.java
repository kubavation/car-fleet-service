package com.durys.jakub.carfleet.requests.state;

import com.durys.jakub.carfleet.requests.Flowable;

public interface Assembler<T extends Flowable> {
    StateConfig<T> assemble();
}

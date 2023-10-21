package com.durys.jakub.carfleet.requests.state;

import com.durys.jakub.carfleet.requests.Request;
import com.durys.jakub.carfleet.requests.WithState;

public interface Assembler<T extends WithState> {
    StateConfig<T> assemble();
}

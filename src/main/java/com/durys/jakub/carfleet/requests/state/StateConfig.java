package com.durys.jakub.carfleet.requests.state;

import com.durys.jakub.carfleet.requests.Request;
import com.durys.jakub.carfleet.requests.WithState;

public interface StateConfig<T extends WithState> {
    State<T> recreate(T request);
    State<T> begin(T request);
}

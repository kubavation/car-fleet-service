package com.durys.jakub.carfleet.requests.state;

import com.durys.jakub.carfleet.requests.Flowable;

public interface StateConfig<T extends Flowable<T>> {
    State<T> recreate(T request);
    State<T> begin(T request);
}

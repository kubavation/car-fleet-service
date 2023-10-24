package com.durys.jakub.carfleet.requests.state;

import com.durys.jakub.carfleet.requests.Flowable;

public interface StateConfig<T extends Flowable<T>> {
    State<T> recreate(T object);
    State<T> begin(T object);
}

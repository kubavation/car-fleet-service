package com.durys.jakub.carfleet.state;

public interface StateConfig<T extends Flowable<T>> {
    State<T> recreate(T object);
    State<T> begin(T object);
}

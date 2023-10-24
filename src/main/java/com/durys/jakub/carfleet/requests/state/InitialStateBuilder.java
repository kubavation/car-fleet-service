package com.durys.jakub.carfleet.requests.state;

import com.durys.jakub.carfleet.requests.Flowable;

public interface InitialStateBuilder<T extends Flowable<T>> {
    StateBuilder.StateTransitionDestinationBuilder<T> beginWith(Enum<?> state);
}

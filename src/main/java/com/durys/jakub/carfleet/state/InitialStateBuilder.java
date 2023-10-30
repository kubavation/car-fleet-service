package com.durys.jakub.carfleet.state;

public interface InitialStateBuilder<T extends Flowable<T>> {
    StateBuilder.StateTransitionDestinationBuilder<T> beginWith(Enum<?> state);
}

package com.durys.jakub.carfleet.state;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.function.Predicate;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
class DefaultStateChangePredicates {

    public static <T extends Flowable<T>> Predicate<State<T>> positive() {
        return flowable -> true;
    }

    public static <T extends Flowable<T>> Predicate<State<T>> negative() {
        return flowable -> true;
    }

}

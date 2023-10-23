package com.durys.jakub.carfleet.requests.state.newimpl;

import com.durys.jakub.carfleet.requests.state.ChangeCommand;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

public class NewStateBuilder<T> {

//    public static class FinalStateConfig<T extends Flowable<T>> {
//
//        private final State<T> state;
//        private final StateBuilder<T> builder;
//
//        FinalStateConfig(State<T> state, StateBuilder<T> builder) {
//            this.state = state;
//            this.builder = builder;
//        }
//
//        public StateBuilder.FinalStateConfig<T> action(BiFunction<T, ChangeCommand, Void> action) {
//            System.out.println(state.getName());
//            System.out.println(action.getClass());
//            state.addAfterStateChangeAction(action);
//            return this;
//        }
//
//        public StateBuilder<T> and() {
//            return builder;
//        }
//
//        public StateBuilder<T> build() {
//            return builder;
//        }
//
//    }

    public static class TransitionBuilder<T> {

        private final NewStateBuilder<T> builder;
        private StateTransition<T> transition;

        public TransitionBuilder(NewStateBuilder<T> builder) {
            this.builder = builder;
        }

        public TransitionBuilder<T> to(Enum<?> to) {

            NewState<T> state = builder.getOrPut(to.name());

            transition = new StateTransition<>(builder.currentState, state);
            return this;
        }

        public TransitionBuilder<T> execute(BiFunction<T, ChangeCommand, Void> action) {
            transition.addAction(action);
            builder.currentState.addTransition(transition);
            return this;
        }

        public NewStateBuilder<T> and() {
            builder.currentState.addTransition(transition);
            return builder;
        }
    }

    private final Map<String, NewState<T>> configuredStates = new HashMap<>();

    private NewState<T> currentState;

    public NewStateBuilder() {
    }

    public TransitionBuilder<T> from(Enum<?> from) {

        this.currentState = getOrPut(from.name());

        return new TransitionBuilder<>(this);
    }


    private NewState<T> getOrPut(String stateName) {
        if (!configuredStates.containsKey(stateName)) {
            configuredStates.put(stateName, new NewState<>(stateName));
        }
        return configuredStates.get(stateName);
    }

    public Map<String, NewState<T>> getConfiguredStates() {
        return configuredStates;
    }
}

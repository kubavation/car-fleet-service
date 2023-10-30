package com.durys.jakub.carfleet.requests.drivertransfer.domain.predicates;


import com.durys.jakub.carfleet.state.Flowable;
import com.durys.jakub.carfleet.state.ChangeCommand;
import com.durys.jakub.carfleet.state.State;
import lombok.extern.slf4j.Slf4j;

import java.util.function.BiFunction;

@Slf4j
public class RequestContentValidVerifier<T extends Flowable<T>> implements BiFunction<State<T>, ChangeCommand, Boolean> {

    @Override
    public Boolean apply(State<T> state, ChangeCommand command) {
        return true;
//                state.getRequest().getFrom() != null
//                        && state.getRequest().getContent().getTo() != null
//                        && state.getRequest().getContent().getPurpose() != null;
    }
}

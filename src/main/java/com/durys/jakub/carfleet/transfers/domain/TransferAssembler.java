package com.durys.jakub.carfleet.transfers.domain;

import com.durys.jakub.carfleet.state.Assembler;
import com.durys.jakub.carfleet.state.StateBuilder;
import com.durys.jakub.carfleet.state.StateConfig;

import static com.durys.jakub.carfleet.transfers.domain.Transfer.State.*;

class TransferAssembler implements Assembler<Transfer> {

    private final StateConfig<Transfer> configuration;

    public TransferAssembler() {
        this.configuration = assemble();
    }

    @Override
    public StateConfig<Transfer> configuration() {
        return configuration;
    }

    @Override
    public StateConfig<Transfer> assemble() {
        return StateBuilder.builderForClass(Transfer.class)
                .beginWith(OPEN)
                .to(CLOSED)
                .execute(null) //todo
            .and()
                .from(OPEN)
                .whenContentChangesTo(OPEN)
            .and()
                .from(CLOSED)
                .to(COMPLETED)
            .and()
                .from(OPEN)
                .to(ARCHIVED)
            .and()
                .from(CLOSED)
                .to(ARCHIVED)
            .build();
    }
}

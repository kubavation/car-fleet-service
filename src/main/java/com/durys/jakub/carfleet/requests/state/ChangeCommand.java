package com.durys.jakub.carfleet.requests.state;

public abstract class ChangeCommand {

    private final String desiredState;

    protected ChangeCommand(String desiredState) {
        this.desiredState = desiredState;
    }

    protected ChangeCommand(Enum<?> desiredState) {
        this.desiredState = desiredState.name();
    }

    public String getDesiredState() {
        return desiredState;
    }
}


package com.durys.jakub.carfleet.requests.state;

public class ChangeCommand {

    private final String desiredState;

    public ChangeCommand(String desiredState) {
        this.desiredState = desiredState;
    }

    public ChangeCommand(Enum<?> desiredState) {
        this.desiredState = desiredState.name();
    }

    public String getDesiredState() {
        return desiredState;
    }
}


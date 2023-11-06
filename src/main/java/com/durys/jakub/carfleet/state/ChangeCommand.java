package com.durys.jakub.carfleet.state;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChangeCommand that = (ChangeCommand) o;
        return Objects.equals(desiredState, that.desiredState);
    }

    @Override
    public int hashCode() {
        return Objects.hash(desiredState);
    }
}


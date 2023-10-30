package com.durys.jakub.carfleet.transfers.domain;

import java.util.Objects;
import java.util.Set;

record TransferStop(String place, Set<TransferParticipant> participants) {

    public void addParticipant(TransferParticipant participant) {
        participants.add(participant);
    }

    public void remove(TransferParticipant participant) {
        participants.removeIf(transferParticipant -> transferParticipant.participantId().equals(participant.participantId()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransferStop that = (TransferStop) o;
        return Objects.equals(place, that.place);
    }

    @Override
    public int hashCode() {
        return Objects.hash(place);
    }
}

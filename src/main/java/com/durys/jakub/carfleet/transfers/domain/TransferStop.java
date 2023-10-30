package com.durys.jakub.carfleet.transfers.domain;

import java.util.Set;

class TransferStop {

    private final String place;
    private final Set<TransferParticipant> participants;

    public TransferStop(String place, Set<TransferParticipant> participants) {
        this.place = place;
        this.participants = participants;
    }

    public void addParticipant(TransferParticipant participant) {
        participants.add(participant);
    }

    public void remove(TransferParticipant participant) {
        participants.removeIf(transferParticipant -> transferParticipant.participantId().equals(participant.participantId()));
    }

    String getPlace() {
        return place;
    }

    Set<TransferParticipant> getParticipants() {
        return participants;
    }
}

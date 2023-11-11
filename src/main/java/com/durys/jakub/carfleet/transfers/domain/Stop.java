package com.durys.jakub.carfleet.transfers.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "TRANSFER_STOP")
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
class Stop {

    @Id
    private final UUID id;

    private final String place;

    @OneToMany
    @JoinColumn(name = "TRANSFER_STOP_ID")
    private final Set<TransferParticipant> participants;

    Stop(String place, Set<TransferParticipant> participants) {
        this.id = UUID.randomUUID();
        this.place = place;
        this.participants = participants;
    }

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
        Stop that = (Stop) o;
        return Objects.equals(place, that.place);
    }

    @Override
    public int hashCode() {
        return Objects.hash(place);
    }

    public String place() {
        return place;
    }

    public Set<TransferParticipant> participants() {
        return participants;
    }


}

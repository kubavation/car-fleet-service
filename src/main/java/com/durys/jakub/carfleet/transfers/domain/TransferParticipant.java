package com.durys.jakub.carfleet.transfers.domain;

import com.durys.jakub.carfleet.sharedkernel.requests.RequestId;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TRANSFER_PARTICIPANT")
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
class TransferParticipant {

    @EmbeddedId
    private final TransferParticipantId id;

    TransferParticipant(TransferParticipantId id) {
        this.id = id;
    }

    TransferParticipant(ParticipantId participantId, RequestId registrationSource) {
        this.id = new TransferParticipantId(participantId, registrationSource);
    }

    ParticipantId participantId() {
        return id.participantId();
    }

    RequestId registrationSource() {
        return id.registrationSource();
    }



}

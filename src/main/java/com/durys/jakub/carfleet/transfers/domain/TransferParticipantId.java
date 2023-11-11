package com.durys.jakub.carfleet.transfers.domain;

import com.durys.jakub.carfleet.sharedkernel.requests.RequestId;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
class TransferParticipantId implements Serializable {

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "PARTICIPANT_ID"))
    private final ParticipantId participantId;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "REGISTRATION_SOURCE_ID"))
    private final RequestId registrationSource;

    TransferParticipantId(ParticipantId participantId, RequestId registrationSource) {
        this.participantId = participantId;
        this.registrationSource = registrationSource;
    }

    ParticipantId participantId() {
        return participantId;
    }

    RequestId registrationSource() {
        return registrationSource;
    }
}

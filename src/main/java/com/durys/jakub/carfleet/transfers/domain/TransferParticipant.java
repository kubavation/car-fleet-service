package com.durys.jakub.carfleet.transfers.domain;

import com.durys.jakub.carfleet.sharedkernel.requests.RequestId;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
class TransferParticipant {

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "PARTICIPANT_ID"))
    private final ParticipantId participantId;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "REGISTRATION_SOURCE_ID"))
    private final RequestId registrationSource;

    TransferParticipant(ParticipantId participantId, RequestId registrationSource) {
        this.participantId = participantId;
        this.registrationSource = registrationSource;
    }

    public ParticipantId participantId() {
        return participantId;
    }

    public RequestId registrationSource() {
        return registrationSource;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (TransferParticipant) obj;
        return Objects.equals(this.participantId, that.participantId) &&
                Objects.equals(this.registrationSource, that.registrationSource);
    }

    @Override
    public int hashCode() {
        return Objects.hash(participantId, registrationSource);
    }


}

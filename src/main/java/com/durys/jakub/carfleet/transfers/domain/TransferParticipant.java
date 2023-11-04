package com.durys.jakub.carfleet.transfers.domain;

import com.durys.jakub.carfleet.sharedkernel.requests.RequestId;

record TransferParticipant(ParticipantId participantId, RequestId registrationSource) {

}

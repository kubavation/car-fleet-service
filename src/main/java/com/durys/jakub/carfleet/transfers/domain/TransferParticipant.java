package com.durys.jakub.carfleet.transfers.domain;

import com.durys.jakub.carfleet.requests.RequestId;

record TransferParticipant(ParticipantId participantId, String name, RequestId registrationSource) {

}

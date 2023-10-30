package com.durys.jakub.carfleet.transfers.domain;

import com.durys.jakub.carfleet.requests.RequestId;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class TransferPathTest {

    @Test
    void shouldAddNewTransferParticipant_whenTransferStopNotExists() {

        ParticipantId participantId = new ParticipantId(UUID.randomUUID());
        String place = "Krakow";
        RequestId requestId = new RequestId(UUID.randomUUID());

        TransferPath transferPath = new TransferPath("Warsaw", new HashSet<>());

        transferPath.addParticipant(participantId, place, requestId);

        assertEquals(1, transferPath.getStops().size());
        assertEquals("Krakow", transferPath.getStops().iterator().next().place());
    }

    @Test
    void shouldAddNewTransferParticipant_whenTransferStopAlreadyExists() {

        ParticipantId participantId = new ParticipantId(UUID.randomUUID());
        String place = "Krakow";
        RequestId requestId = new RequestId(UUID.randomUUID());

        TransferPath transferPath = new TransferPath("Warsaw", new HashSet<>(
                Arrays.asList(new TransferStop("Krakow",
                    new HashSet<>(
                            Arrays.asList(new TransferParticipant(
                                    new ParticipantId(UUID.randomUUID()),
                                    new RequestId(UUID.randomUUID())))
                    )
                )
        )));

        transferPath.addParticipant(participantId, place, requestId);

        assertEquals(1, transferPath.getStops().size());
        assertEquals(2, transferPath.getStops().iterator().next().participants().size());
    }

    @Test
    void shouldRemoveTransferParticipant() {

        ParticipantId participantId = new ParticipantId(UUID.randomUUID());
        String place = "Krakow";
        RequestId requestId = new RequestId(UUID.randomUUID());

        TransferPath transferPath = new TransferPath("Warsaw", new HashSet<>(
                Arrays.asList(new TransferStop("Krakow",
                                new HashSet<>(
                                        Arrays.asList(
                                                new TransferParticipant(
                                                    new ParticipantId(UUID.randomUUID()),
                                                    new RequestId(UUID.randomUUID())),
                                                new TransferParticipant(
                                                    participantId, requestId)

                                        )
                                )
                        )
                )));

        transferPath.removeParticipant(participantId, place, requestId);

        assertEquals(1, transferPath.getStops().size());
        assertEquals(1, transferPath.getStops().iterator().next().participants().size());
    }

    @Test
    void shouldRemoveTransferParticipantAndTransferStopWhenThereIsNoParticipants() {

        ParticipantId participantId = new ParticipantId(UUID.randomUUID());
        String place = "Krakow";
        RequestId requestId = new RequestId(UUID.randomUUID());

        TransferPath transferPath = new TransferPath("Warsaw", new HashSet<>(
                Arrays.asList(new TransferStop("Krakow",
                                new HashSet<>(
                                        Arrays.asList(
                                                new TransferParticipant(
                                                        participantId, requestId)

                                        )
                                )
                        )
                )));

        transferPath.removeParticipant(participantId, place, requestId);

        assertEquals(0, transferPath.getStops().size());
    }

}
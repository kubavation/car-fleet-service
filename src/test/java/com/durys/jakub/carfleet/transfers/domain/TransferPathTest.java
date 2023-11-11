package com.durys.jakub.carfleet.transfers.domain;

import com.durys.jakub.carfleet.cars.domain.CarId;
import com.durys.jakub.carfleet.sharedkernel.requests.RequestId;
import com.durys.jakub.carfleet.sharedkernel.requests.RequesterId;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class TransferPathTest {

    @Test
    void shouldAddNewTransferParticipant_whenTransferStopNotExists() {

        ParticipantId participantId = new ParticipantId(UUID.randomUUID());
        String place = "Krakow";
        RequestId requestId = new RequestId(UUID.randomUUID());

        Transfer transfer = TransferFactory.single(new RequesterId(UUID.randomUUID()),"Warsaw",
                LocalDateTime.now(), LocalDateTime.now().plusDays(1), new CarId(UUID.randomUUID()));


        transfer.addParticipant(participantId, place, requestId);

        assertEquals(1, transfer.stops().size());
        assertEquals("Krakow", transfer.stops().iterator().next().place());
    }

    @Test
    void shouldAddNewTransferParticipant_whenTransferStopAlreadyExists() {

        ParticipantId participantId = new ParticipantId(UUID.randomUUID());
        String place = "Krakow";
        RequestId requestId = new RequestId(UUID.randomUUID());

        Transfer transfer = TransferFactory.single(new RequesterId(UUID.randomUUID()), "Warsaw",
                LocalDateTime.now(), LocalDateTime.now().plusDays(1), new CarId(UUID.randomUUID()));

        transfer.addParticipant(new ParticipantId(UUID.randomUUID()), "Krakow", new RequestId(UUID.randomUUID()));
        transfer.addParticipant(participantId, place, requestId);

        assertEquals(1, transfer.stops().size());
        assertEquals(2, transfer.stops().iterator().next().participants().size());
    }

    @Test
    void shouldRemoveTransferParticipant() {

        ParticipantId participantId = new ParticipantId(UUID.randomUUID());
        String place = "Krakow";
        RequestId requestId = new RequestId(UUID.randomUUID());

        Transfer transfer = TransferFactory.single(new RequesterId(UUID.randomUUID()), "Warsaw",
                LocalDateTime.now(), LocalDateTime.now().plusDays(1), new CarId(UUID.randomUUID()));
        transfer.addParticipant(participantId, "Krakow", new RequestId(UUID.randomUUID()));
        transfer.addParticipant(new ParticipantId(UUID.randomUUID()), "Lodz", new RequestId(UUID.randomUUID()));

        transfer.removeParticipant(participantId, place, requestId);

        assertEquals(1, transfer.stops().size());
        assertEquals(1, transfer.stops().iterator().next().participants().size());
    }

    @Test
    void shouldRemoveTransferParticipantAndTransferStopWhenThereIsNoParticipants() {

        ParticipantId participantId = new ParticipantId(UUID.randomUUID());
        String place = "Krakow";
        RequestId requestId = new RequestId(UUID.randomUUID());

        Transfer transfer = TransferFactory.single(new RequesterId(UUID.randomUUID()),"Warsaw",
                LocalDateTime.now(), LocalDateTime.now().plusDays(1), new CarId(UUID.randomUUID()));
        transfer.addParticipant(participantId, place, new RequestId(UUID.randomUUID()));

        transfer.removeParticipant(participantId, place, requestId);

        assertEquals(0, transfer.stops().size());
    }

}
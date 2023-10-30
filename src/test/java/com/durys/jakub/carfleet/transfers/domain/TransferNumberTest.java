package com.durys.jakub.carfleet.transfers.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class TransferNumberTest {

    @Test
    void shouldCreateCorrectTransferNumber() {

        TransferPath transferPath = new TransferPath("Warsaw", Set.of());
        LocalDateTime at = LocalDate.of(2023, 1, 1).atStartOfDay();
        Transfer.Type tranferType = Transfer.Type.Group;

        TransferNumber transferNumber = new TransferNumber(transferPath, tranferType, at);

        assertEquals("Warsaw-Group_2023-01-01", transferNumber.value());
    }

}
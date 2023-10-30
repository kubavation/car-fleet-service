package com.durys.jakub.carfleet.transfers.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TransferNumberTest {

    @Test
    void shouldCreateCorrectTransferNumber() {

        TransferPath transferPath = new TransferPath("Warsaw", Set.of());
        TransferPeriod period = new TransferPeriod(
                LocalDate.of(2023, 1, 1).atStartOfDay(),
                LocalDate.of(2023, 1, 2).atStartOfDay()
        );
        Transfer.Type tranferType = Transfer.Type.Group;

        TransferNumber transferNumber = new TransferNumber(transferPath, tranferType, period);

        assertEquals("Warsaw-Group_2023-01-01", transferNumber.value());
    }

}
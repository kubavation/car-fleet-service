package com.durys.jakub.carfleet.drivers.domain.availability;

import com.durys.jakub.carfleet.drivers.domain.Driver;
import com.durys.jakub.carfleet.drivers.domain.DriverId;
import com.durys.jakub.carfleet.drivers.domain.DriverRepository;
import com.durys.jakub.carfleet.drivers.infrastructure.availability.DefaultDriverAvailabilityService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DriverAvailabilityServiceTest {

    private final DriverRepository driverRepository = Mockito.mock(DriverRepository.class);

    private final DriverAvailabilityService driverAvailabilityService
            = new DefaultDriverAvailabilityService(driverRepository);

    private final DriverId driverId = new DriverId(UUID.randomUUID());

    @Test
    void shouldReturnDriverIsAvailable() {

        Mockito.when(driverRepository.findById(driverId)).thenReturn(
                Optional.of(createWithAbsencesBetween(driverId,
                        LocalDate.of(2023, 2, 1),
                        LocalDate.of(2023, 2, 3))));

        boolean available = driverAvailabilityService.available(driverId,
                LocalDate.of(2023, 1, 2).atStartOfDay(),
                LocalDate.of(2023, 1, 3).atStartOfDay());

        assertTrue(available);

    }

    @Test
    void shouldReturnDriverIsNotAvailableWhenDriverIsArchived() {

        Driver driver = createWithAbsencesBetween(driverId,
                LocalDate.of(2023, 2, 1),
                LocalDate.of(2023, 2, 3));
        driver.archive();

        Mockito.when(driverRepository.findById(driverId)).thenReturn(
                Optional.of(driver));

        boolean available = driverAvailabilityService.available(driverId,
                LocalDate.of(2023, 1, 2).atStartOfDay(),
                LocalDate.of(2023, 1, 3).atStartOfDay());

        assertFalse(available);

    }

    @Test
    void shouldReturnDriverIsNotAvailableWhenDrivesIsAbsent() {

        Driver driver = createWithAbsencesBetween(driverId,
                LocalDate.of(2023, 1, 3),
                LocalDate.of(2023, 1, 4));
        driver.archive();

        Mockito.when(driverRepository.findById(driverId)).thenReturn(
                Optional.of(driver));

        boolean available = driverAvailabilityService.available(driverId,
                LocalDate.of(2023, 1, 2).atStartOfDay(),
                LocalDate.of(2023, 1, 3).atStartOfDay());

        assertFalse(available);

    }


    private static Driver createWithAbsencesBetween(DriverId driverId, LocalDate absenceFrom, LocalDate absenceTo) {
        Driver driver = new Driver(driverId, "Test", "Test");
        driver.markAsInactiveBetween(absenceFrom, absenceTo);
        return driver;
    }

}
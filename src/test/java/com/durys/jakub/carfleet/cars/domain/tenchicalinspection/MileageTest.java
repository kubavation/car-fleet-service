package com.durys.jakub.carfleet.cars.domain.tenchicalinspection;

import com.durys.jakub.carfleet.common.errors.ValidationError;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class MileageTest {

    @Test
    void shouldSuccessfullyCreateMileage() {

        BigDecimal value = new BigDecimal("200.5");

        Mileage mileage = new Mileage(value);

        assertEquals(value, mileage.value());
    }

    @Test
    void shouldThrowExceptionWhenMileageValueIsEmpty() {

        BigDecimal value = null;

        ValidationError validationError = assertThrows(ValidationError.class, () -> new Mileage(value));
        assertEquals("Mileage value cannot be empty", validationError.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenMileageValueLessThan0() {

        BigDecimal value = new BigDecimal("-1");

        ValidationError validationError = assertThrows(ValidationError.class, () -> new Mileage(value));
        assertEquals("Mileage value cannot be less than 0", validationError.getMessage());
    }


}

package com.room.reservation;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.room.reservation.dto.ReservationRequestDTO;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public class ReservationValidationTest {

    private Validator validator;

    @BeforeEach
    void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void shouldFailWhenGuestNameIsBlank() {
        ReservationRequestDTO dto = validDTO();
        dto.setGuestName("");

        Set<ConstraintViolation<ReservationRequestDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().toLowerCase().contains("guest name")));
    }

    @Test
    void shouldFailWhenContactNumberIsInvalid() {
        ReservationRequestDTO dto = validDTO();
        dto.setContactNumber("07A123"); // invalid

        Set<ConstraintViolation<ReservationRequestDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().toLowerCase().contains("digits")));
    }

    @Test
    void shouldPassForValidReservationRequest() {
        ReservationRequestDTO dto = validDTO();

        Set<ConstraintViolation<ReservationRequestDTO>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
    }

    private ReservationRequestDTO validDTO() {
        ReservationRequestDTO dto = new ReservationRequestDTO();
        dto.setReservationNumber("RES3001");
        dto.setGuestName("Kamal Perera");
        dto.setAddress("Galle, Sri Lanka");
        dto.setContactNumber("0771234567");
        dto.setRoomType("SINGLE");
        dto.setCheckInDate(LocalDate.of(2026, 2, 20));
        dto.setCheckOutDate(LocalDate.of(2026, 2, 22));
        return dto;
    }
}

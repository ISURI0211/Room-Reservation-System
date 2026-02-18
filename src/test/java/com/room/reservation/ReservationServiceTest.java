package com.room.reservation;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.room.reservation.dto.ReservationRequestDTO;
import com.room.reservation.entity.Reservation;
import com.room.reservation.exception.DuplicateResourceException;
import com.room.reservation.repository.ReservationRepository;
import com.room.reservation.service.ReservationService;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @InjectMocks
    private ReservationService reservationService;

    @Test
    void shouldSaveReservationWhenValid() {
        ReservationRequestDTO dto = validDTO();

        when(reservationRepository.existsByReservationNumber("RES5001")).thenReturn(false);
        when(reservationRepository.save(any(Reservation.class))).thenAnswer(inv -> inv.getArgument(0));

        Reservation saved = reservationService.createReservation(dto);

        assertEquals("RES5001", saved.getReservationNumber());
        assertEquals("SINGLE", saved.getRoomType());
        verify(reservationRepository, times(1)).save(any(Reservation.class));
    }

    @Test
    void shouldThrowDuplicateWhenReservationNumberExists() {
        ReservationRequestDTO dto = validDTO();

        when(reservationRepository.existsByReservationNumber("RES5001")).thenReturn(true);

        assertThrows(DuplicateResourceException.class, () ->
            reservationService.createReservation(dto)
        );
    }

    @Test
    void shouldThrowIllegalArgumentWhenDatesInvalid() {
        ReservationRequestDTO dto = validDTO();
        dto.setCheckInDate(LocalDate.of(2026, 2, 22));
        dto.setCheckOutDate(LocalDate.of(2026, 2, 20)); // invalid

        // ❌ Remove this line (it's unnecessary because code throws before repo call)
        // when(reservationRepository.existsByReservationNumber("RES5001")).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () ->
            reservationService.createReservation(dto)
        );
    }


    private ReservationRequestDTO validDTO() {
        ReservationRequestDTO dto = new ReservationRequestDTO();
        dto.setReservationNumber("RES5001");
        dto.setGuestName("Kamal Perera");
        dto.setAddress("Galle, Sri Lanka");
        dto.setContactNumber("0771234567");
        dto.setRoomType("SINGLE");
        dto.setCheckInDate(LocalDate.of(2026, 2, 20));
        dto.setCheckOutDate(LocalDate.of(2026, 2, 22));
        return dto;
    }
}

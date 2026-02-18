package com.room.reservation;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.room.reservation.dto.BillResponseDTO;
import com.room.reservation.entity.Reservation;
import com.room.reservation.entity.RoomRate;
import com.room.reservation.exception.NotFoundException;
import com.room.reservation.repository.ReservationRepository;
import com.room.reservation.repository.RoomRateRepository;
import com.room.reservation.service.BillingService;

@ExtendWith(MockitoExtension.class)
public class BillingServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private RoomRateRepository roomRateRepository;

    @InjectMocks
    private BillingService billingService;

    @Test
    void shouldCalculateCorrectBillTotal() {
        Reservation r = new Reservation();
        r.setReservationNumber("RES1001");
        r.setGuestName("Kamal Perera");
        r.setRoomType("SINGLE");
        r.setCheckInDate(LocalDate.of(2026, 2, 20));
        r.setCheckOutDate(LocalDate.of(2026, 2, 22)); // 2 nights

        when(reservationRepository.findByReservationNumber("RES1001")).thenReturn(Optional.of(r));
        when(roomRateRepository.findByRoomType("SINGLE")).thenReturn(Optional.of(new RoomRate("SINGLE", 10000)));

        BillResponseDTO bill = billingService.generateBillByReservationNumber("RES1001");

        assertEquals(2, bill.getNights());
        assertEquals(10000.0, bill.getRatePerNight());
        assertEquals(20000.0, bill.getTotalAmount());
    }

    @Test
    void shouldThrowWhenReservationNotFound() {
        when(reservationRepository.findByReservationNumber("UNKNOWN")).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () ->
            billingService.generateBillByReservationNumber("UNKNOWN")
        );
    }

    @Test
    void shouldThrowWhenRoomRateNotFound() {
        Reservation r = new Reservation();
        r.setReservationNumber("RES2002");
        r.setGuestName("Nimal");
        r.setRoomType("SUITE");
        r.setCheckInDate(LocalDate.of(2026, 2, 20));
        r.setCheckOutDate(LocalDate.of(2026, 2, 21));

        when(reservationRepository.findByReservationNumber("RES2002")).thenReturn(Optional.of(r));
        when(roomRateRepository.findByRoomType("SUITE")).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () ->
            billingService.generateBillByReservationNumber("RES2002")
        );
    }
}

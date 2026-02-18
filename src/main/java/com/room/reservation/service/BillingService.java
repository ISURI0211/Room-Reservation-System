package com.room.reservation.service;

import java.time.temporal.ChronoUnit;

import org.springframework.stereotype.Service;

import com.room.reservation.dto.BillResponseDTO;
import com.room.reservation.entity.Reservation;
import com.room.reservation.entity.RoomRate;
import com.room.reservation.exception.NotFoundException;
import com.room.reservation.repository.ReservationRepository;
import com.room.reservation.repository.RoomRateRepository;

@Service
public class BillingService {

    private final ReservationRepository reservationRepository;
    private final RoomRateRepository roomRateRepository;

    public BillingService(ReservationRepository reservationRepository,
                          RoomRateRepository roomRateRepository) {
        this.reservationRepository = reservationRepository;
        this.roomRateRepository = roomRateRepository;
    }

    public BillResponseDTO generateBillByReservationNumber(String reservationNumber) {

        Reservation r = reservationRepository.findByReservationNumber(reservationNumber.trim())
                .orElseThrow(() -> new NotFoundException("Reservation not found: " + reservationNumber));

        String type = r.getRoomType().trim().toUpperCase();

        RoomRate rate = roomRateRepository.findByRoomType(type)
                .orElseThrow(() -> new NotFoundException("Room rate not found for type: " + type));

        long nights = ChronoUnit.DAYS.between(r.getCheckInDate(), r.getCheckOutDate());
        if (nights <= 0) {
            throw new IllegalArgumentException("Invalid stay duration. Check-out must be after check-in.");
        }

        double total = nights * rate.getPricePerNight();

        return new BillResponseDTO(
                r.getReservationNumber(),
                r.getGuestName(),
                type,
                r.getCheckInDate(),
                r.getCheckOutDate(),
                nights,
                rate.getPricePerNight(),
                total
        );
    }
}

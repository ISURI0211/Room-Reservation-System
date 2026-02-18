package com.room.reservation.service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.room.reservation.dto.ReservationRequestDTO;
import com.room.reservation.entity.Reservation;
import com.room.reservation.exception.DuplicateResourceException;
import com.room.reservation.exception.NotFoundException;
import com.room.reservation.repository.ReservationRepository;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Transactional
    public Reservation createReservation(ReservationRequestDTO dto) {

        // date rule: check-in must be before check-out
        LocalDate in = dto.getCheckInDate();
        LocalDate out = dto.getCheckOutDate();
        if (in == null || out == null || !in.isBefore(out)) {
            throw new IllegalArgumentException("Check-in date must be before check-out date");
        }

        // unique reservation number
        if (reservationRepository.existsByReservationNumber(dto.getReservationNumber().trim())) {
            throw new DuplicateResourceException("Reservation number already exists: " + dto.getReservationNumber());
        }

        Reservation r = new Reservation();
        r.setReservationNumber(dto.getReservationNumber().trim());
        r.setGuestName(dto.getGuestName().trim());
        r.setAddress(dto.getAddress().trim());
        r.setContactNumber(dto.getContactNumber().trim());
        r.setRoomType(dto.getRoomType().trim().toUpperCase());
        r.setCheckInDate(dto.getCheckInDate());
        r.setCheckOutDate(dto.getCheckOutDate());

        return reservationRepository.save(r);
    }

    public Reservation getByReservationNumber(String reservationNumber) {
        return reservationRepository.findByReservationNumber(reservationNumber.trim())
                .orElseThrow(() -> new NotFoundException("Reservation not found: " + reservationNumber));
    }

    public Reservation getById(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Reservation not found with id: " + id));
    }
}

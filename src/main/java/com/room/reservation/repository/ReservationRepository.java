package com.room.reservation.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.room.reservation.entity.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    Optional<Reservation> findByReservationNumber(String reservationNumber);
    boolean existsByReservationNumber(String reservationNumber);

    // ✅ Daily report: reservations where a given date falls inside the stay period
    List<Reservation> findByCheckInDateLessThanEqualAndCheckOutDateGreaterThan(LocalDate date1, LocalDate date2);

    // ✅ Revenue report: reservations that overlap a date range
    List<Reservation> findByCheckInDateLessThanEqualAndCheckOutDateGreaterThanEqual(LocalDate to, LocalDate from);
}

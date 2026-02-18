package com.room.reservation.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.stereotype.Service;

import com.room.reservation.dto.ReservationReportItemDTO;
import com.room.reservation.dto.RevenueReportDTO;
import com.room.reservation.entity.Reservation;
import com.room.reservation.entity.RoomRate;
import com.room.reservation.exception.NotFoundException;
import com.room.reservation.repository.ReservationRepository;
import com.room.reservation.repository.RoomRateRepository;

@Service
public class ReportService {

    private final ReservationRepository reservationRepository;
    private final RoomRateRepository roomRateRepository;

    public ReportService(ReservationRepository reservationRepository, RoomRateRepository roomRateRepository) {
        this.reservationRepository = reservationRepository;
        this.roomRateRepository = roomRateRepository;
    }

    // ✅ Daily reservations report
    public List<ReservationReportItemDTO> getDailyReservations(LocalDate date) {
        List<Reservation> list = reservationRepository
                .findByCheckInDateLessThanEqualAndCheckOutDateGreaterThan(date, date);

        return list.stream()
                .map(r -> new ReservationReportItemDTO(
                        r.getReservationNumber(),
                        r.getGuestName(),
                        r.getContactNumber(),
                        r.getRoomType(),
                        r.getCheckInDate(),
                        r.getCheckOutDate()
                ))
                .toList();
    }

    // ✅ Revenue report for date range
    public RevenueReportDTO getRevenue(LocalDate from, LocalDate to) {
        if (from == null || to == null || from.isAfter(to)) {
            throw new IllegalArgumentException("Invalid date range. 'from' must be before or equal to 'to'.");
        }

        List<Reservation> reservations = reservationRepository
                .findByCheckInDateLessThanEqualAndCheckOutDateGreaterThanEqual(to, from);

        double totalRevenue = 0.0;

        for (Reservation r : reservations) {
            String type = r.getRoomType().trim().toUpperCase();

            RoomRate rate = roomRateRepository.findByRoomType(type)
                    .orElseThrow(() -> new NotFoundException("Room rate not found for type: " + type));

            // Calculate nights overlapping within the range
            LocalDate stayStart = r.getCheckInDate().isBefore(from) ? from : r.getCheckInDate();
            LocalDate stayEnd = r.getCheckOutDate().isAfter(to.plusDays(1)) ? to.plusDays(1) : r.getCheckOutDate();

            long nights = ChronoUnit.DAYS.between(stayStart, stayEnd);
            if (nights > 0) {
                totalRevenue += nights * rate.getPricePerNight();
            }
        }

        return new RevenueReportDTO(from, to, reservations.size(), totalRevenue);
    }
}

package com.room.reservation.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.room.reservation.dto.ReservationRequestDTO;
import com.room.reservation.entity.Reservation;
import com.room.reservation.service.ReservationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/reservations")
@Validated
@CrossOrigin(origins = "*") // later we can restrict to frontend origin
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    // Add Reservation
    @PostMapping
    public ResponseEntity<Reservation> create(@Valid @RequestBody ReservationRequestDTO dto) {
        Reservation saved = reservationService.createReservation(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // View by ID
    @GetMapping("/{id}")
    public ResponseEntity<Reservation> getById(@PathVariable Long id) {
        return ResponseEntity.ok(reservationService.getById(id));
    }

    // View by reservation number
    @GetMapping("/by-number/{reservationNumber}")
    public ResponseEntity<Reservation> getByReservationNumber(@PathVariable String reservationNumber) {
        return ResponseEntity.ok(reservationService.getByReservationNumber(reservationNumber));
    }
}

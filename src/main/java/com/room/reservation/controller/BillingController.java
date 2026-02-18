package com.room.reservation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.room.reservation.dto.BillResponseDTO;
import com.room.reservation.service.BillingService;

@RestController
@RequestMapping("/billing")
@CrossOrigin(origins = "*")
public class BillingController {

    private final BillingService billingService;

    public BillingController(BillingService billingService) {
        this.billingService = billingService;
    }

    // Example: GET /billing/RES1001
    @GetMapping("/{reservationNumber}")
    public ResponseEntity<BillResponseDTO> getBill(@PathVariable String reservationNumber) {
        return ResponseEntity.ok(billingService.generateBillByReservationNumber(reservationNumber));
    }
}

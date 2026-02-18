package com.room.reservation.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.room.reservation.dto.ReservationReportItemDTO;
import com.room.reservation.dto.RevenueReportDTO;
import com.room.reservation.service.ReportService;

@RestController
@RequestMapping("/reports")
@CrossOrigin(origins = "*")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    // ✅ Daily reservations: /reports/daily?date=2026-02-20
    @GetMapping("/daily")
    public ResponseEntity<List<ReservationReportItemDTO>> daily(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(reportService.getDailyReservations(date));
    }

    // ✅ Revenue report: /reports/revenue?from=2026-02-01&to=2026-02-28
    @GetMapping("/revenue")
    public ResponseEntity<RevenueReportDTO> revenue(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        return ResponseEntity.ok(reportService.getRevenue(from, to));
    }
}

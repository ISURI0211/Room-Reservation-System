package com.room.reservation.dto;

import java.time.LocalDate;

public class RevenueReportDTO {

    private LocalDate fromDate;
    private LocalDate toDate;
    private long totalReservations;
    private double totalRevenue;

    public RevenueReportDTO() {}

    public RevenueReportDTO(LocalDate fromDate, LocalDate toDate, long totalReservations, double totalRevenue) {
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.totalReservations = totalReservations;
        this.totalRevenue = totalRevenue;
    }

    public LocalDate getFromDate() { return fromDate; }
    public void setFromDate(LocalDate fromDate) { this.fromDate = fromDate; }

    public LocalDate getToDate() { return toDate; }
    public void setToDate(LocalDate toDate) { this.toDate = toDate; }

    public long getTotalReservations() { return totalReservations; }
    public void setTotalReservations(long totalReservations) { this.totalReservations = totalReservations; }

    public double getTotalRevenue() { return totalRevenue; }
    public void setTotalRevenue(double totalRevenue) { this.totalRevenue = totalRevenue; }
}

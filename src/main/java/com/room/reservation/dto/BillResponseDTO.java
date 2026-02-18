package com.room.reservation.dto;

import java.time.LocalDate;

public class BillResponseDTO {

    private String reservationNumber;
    private String guestName;
    private String roomType;

    private LocalDate checkInDate;
    private LocalDate checkOutDate;

    private long nights;
    private double ratePerNight;
    private double totalAmount;

    public BillResponseDTO() {}

    public BillResponseDTO(String reservationNumber, String guestName, String roomType,
                           LocalDate checkInDate, LocalDate checkOutDate,
                           long nights, double ratePerNight, double totalAmount) {
        this.reservationNumber = reservationNumber;
        this.guestName = guestName;
        this.roomType = roomType;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.nights = nights;
        this.ratePerNight = ratePerNight;
        this.totalAmount = totalAmount;
    }

    public String getReservationNumber() { return reservationNumber; }
    public void setReservationNumber(String reservationNumber) { this.reservationNumber = reservationNumber; }

    public String getGuestName() { return guestName; }
    public void setGuestName(String guestName) { this.guestName = guestName; }

    public String getRoomType() { return roomType; }
    public void setRoomType(String roomType) { this.roomType = roomType; }

    public LocalDate getCheckInDate() { return checkInDate; }
    public void setCheckInDate(LocalDate checkInDate) { this.checkInDate = checkInDate; }

    public LocalDate getCheckOutDate() { return checkOutDate; }
    public void setCheckOutDate(LocalDate checkOutDate) { this.checkOutDate = checkOutDate; }

    public long getNights() { return nights; }
    public void setNights(long nights) { this.nights = nights; }

    public double getRatePerNight() { return ratePerNight; }
    public void setRatePerNight(double ratePerNight) { this.ratePerNight = ratePerNight; }

    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }
}

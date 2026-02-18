package com.room.reservation.dto;

import java.time.LocalDate;

public class ReservationReportItemDTO {

    private String reservationNumber;
    private String guestName;
    private String contactNumber;
    private String roomType;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;

    public ReservationReportItemDTO() {}

    public ReservationReportItemDTO(String reservationNumber, String guestName, String contactNumber,
                                    String roomType, LocalDate checkInDate, LocalDate checkOutDate) {
        this.reservationNumber = reservationNumber;
        this.guestName = guestName;
        this.contactNumber = contactNumber;
        this.roomType = roomType;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    public String getReservationNumber() { return reservationNumber; }
    public void setReservationNumber(String reservationNumber) { this.reservationNumber = reservationNumber; }

    public String getGuestName() { return guestName; }
    public void setGuestName(String guestName) { this.guestName = guestName; }

    public String getContactNumber() { return contactNumber; }
    public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber; }

    public String getRoomType() { return roomType; }
    public void setRoomType(String roomType) { this.roomType = roomType; }

    public LocalDate getCheckInDate() { return checkInDate; }
    public void setCheckInDate(LocalDate checkInDate) { this.checkInDate = checkInDate; }

    public LocalDate getCheckOutDate() { return checkOutDate; }
    public void setCheckOutDate(LocalDate checkOutDate) { this.checkOutDate = checkOutDate; }
}
